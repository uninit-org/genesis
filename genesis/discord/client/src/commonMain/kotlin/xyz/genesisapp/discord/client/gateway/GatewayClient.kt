package xyz.genesisapp.discord.client.gateway

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.genesisapp.common.fytix.EventBus
import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.entities.guild.Channel
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.discord.client.gateway.handlers.initGatewayHandlers
import xyz.genesisapp.discord.client.gateway.serializers.GatewaySerializer
import xyz.genesisapp.discord.client.gateway.types.GatewayEvent
import xyz.genesisapp.discord.client.gateway.types.events.LastMessages
import xyz.genesisapp.discord.client.gateway.entities.events.GatewayIdentify
import xyz.genesisapp.discord.client.gateway.entities.events.GatewayRequestMessages
import xyz.genesisapp.discord.client.gateway.entities.events.GatewayResume

class GatewayClient(
    engineFactory: HttpClientEngineFactory<*>,
    val parentClient: GenesisClient,
    val eventBus: EventBus = parentClient,
) : EventEmitter() {
    private val http = HttpClient(engineFactory) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(message, null, "Gateway")
                }
            }
            level = io.ktor.client.plugins.logging.LogLevel.HEADERS
            filter {
                parentClient.logLevel >= LogLevel.NETWORK
            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    val scope = CoroutineScope(Dispatchers.IO)


    var websocket: DefaultClientWebSocketSession? = null


    @PublishedApi
    internal val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    inline fun <reified T> send(data: T) {
        try {
            val text = if (data is String) {
                data
            } else {
                json.encodeToString(data)
            }
            if (websocket?.isActive == true) {
                val frame = Frame.Text(text)
                scope.launch {
                    websocket!!.send(frame)
                }
            } else {
                if (parentClient.logLevel >= LogLevel.ERROR) Napier.e(
                    "Websocket is not active",
                    null,
                    "Gateway"
                )
            }
        } catch (e: Exception) {
            if (parentClient.logLevel >= LogLevel.ERROR) Napier.e(
                "Error sending data",
                e,
                "Gateway"
            )
        }
    }

    fun parseMessage(message: String) = json.decodeFromString(GatewaySerializer, message)

    private var isDisconnecting = false

    var token: String = ""

    fun connect() {
        if (websocket?.isActive == true) return
        var resumePacket: GatewayEvent<GatewayResume>? = null
        var gatewayUrl = "wss://gateway.discord.gg/?v=9&encoding=json"
        var seq: Int? = null
        var retries = 0
//        once<Ready>("READY") { ready ->
//            resumePacket = GatewayEvent(
//                6,
//                null,
//                null,
//                GatewayResume(
//                    token = token,
//                    sessionId = ready.sessionId,
//                    seq = seq!!
//                )
//            )
//            gatewayUrl = ready.resumeGatewayUrl
//        }
        scope.launch {
            while (!isDisconnecting) {
                if (retries > 10) {
                    if (parentClient.logLevel >= LogLevel.ERROR) Napier.e(
                        "Gateway failed to connect",
                        null,
                        "Gateway"
                    )
                    break
                }
                try {
                    http.webSocket(gatewayUrl) {
                        websocket = this
                        if (resumePacket === null) {
                            val identifyPacket = GatewayEvent(
                                2, null, null, GatewayIdentify(
                                    token = token,
                                )
                            )
                            send(identifyPacket)
                        } else {
                            retries++
                            if (parentClient.logLevel >= LogLevel.DEBUG)
                                Napier.d("Resuming connection", null, "Gateway")
                            send(resumePacket)
                        }
                        while (true) {
                            val othersMessage = incoming.receive() as? Frame.Text ?: continue
                            val text = othersMessage.readText()
                            emit("raw", text)
                            try {
                                val json = parseMessage(text) as GatewayEvent<*>

                                seq = json.s

                                val event = json.t ?: json.op.toString()

                                if (parentClient.logLevel >= LogLevel.NETWORK)
                                    Napier.d("Received event $event", null, "Gateway")

                                emit(event, json.d)
                                emit("${event}Raw", text)
                            } catch (e: Exception) {
                                val blacklist = listOf(
                                    "Unknown opcode",
                                    "Unknown event",
                                    "Channel was closed"
                                )
                                var isBlacklisted = false
                                for (blacklisted in blacklist) {
                                    if (e.message?.contains(blacklisted) == true) {
                                        isBlacklisted = true
                                        if (parentClient.logLevel >= LogLevel.NETWORK)
                                            Napier.d("Error parsing message", e, "Gateway")
                                        break
                                    }
                                }
                                if (!isBlacklisted)
                                    if (parentClient.logLevel >= LogLevel.ERROR) Napier.e(
                                        "Error parsing message",
                                        e,
                                        "Gateway"
                                    )
                            }
                        }
                    }
                } catch (_: Exception) {
                }
            }
            isDisconnecting = false
            emit("GATEWAY_DISCONNECT", true)
        }
    }

    init {
        initGatewayHandlers(parentClient, this)
    }

    fun disconnect() {
        isDisconnecting = true
        scope.launch {
            websocket?.close()
        }
    }

    suspend fun getLastMessages(channel: Channel) {
        val packet = GatewayEvent(
            34, null, null, GatewayRequestMessages(
                channel.guildId.toString(),
                listOf(channel.id.toString())
            )
        )

        send(packet)

        while (true) {
            val msg = suspendOnce<LastMessages>("LAST_MESSAGES")
            if (msg.guildId == channel.guildId) {
                channel.addApiMessages(msg.messages)
                break
            }
        }
        return
    }
}