package xyz.genesisapp.discord.client.gateway

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.entities.guild.Channel
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.discord.client.gateway.handlers.initGatewayHandlers
import xyz.genesisapp.discord.client.gateway.serializers.GatewaySerializer
import xyz.genesisapp.discord.client.gateway.types.GatewayEvent
import xyz.genesisapp.discord.client.gateway.types.events.LastMessages
import xyz.genesisapp.discord.client.gateway.types.events.opCode.GatewayIdentify
import xyz.genesisapp.discord.client.gateway.types.events.opCode.GatewayRequestMessages

class GatewayClient(
    engineFactory: HttpClientEngineFactory<*>,
    val genesisClient: GenesisClient
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
                genesisClient.logLevel >= LogLevel.NETWORK
            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    val scope = CoroutineScope(Dispatchers.IO)


    var websocket: DefaultClientWebSocketSession? = null

    var sessionId: String? = null

    @PublishedApi
    internal val JsonClient = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    inline fun <reified T> send(data: T) {
        try {
            val text = if (data is String) {
                data
            } else {
                JsonClient.encodeToString(data)
            }
            if (websocket?.isActive == true) {
                val frame = Frame.Text(text)
                scope.launch {
                    websocket!!.send(frame)
                }
            } else {
                Napier.e("Websocket is not active", null, "Gateway")
            }
        } catch (e: Exception) {
            Napier.e("Error sending data", e, "Gateway")
        }
    }

    fun parseMessage(message: String) = JsonClient.decodeFromString(GatewaySerializer, message)

    fun connect(token: String, os: String = "genesis") {
        if (websocket?.isActive == true) return
        scope.launch {
            http.webSocket("wss://gateway.discord.gg/?v=9&encoding=json") {
                websocket = this
                val identifyPacket = GatewayEvent(
                    2, null, null, GatewayIdentify(
                        token = token,
                    )
                )
                send(identifyPacket)
                while (true) {
                    val othersMessage = incoming.receive() as? Frame.Text ?: continue
                    val text = othersMessage.readText()
                    emit("raw", text)
                    try {
                        val json = parseMessage(text) as GatewayEvent<*>

                        val event = json.t ?: json.op.toString()

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
                                if (e.message !== null) Napier.v(e.message!!, null, "Gateway")
                                isBlacklisted = true
                                break
                            }
                        }
                        if (!isBlacklisted)
                            Napier.e("Error parsing message", e, "Gateway")
                    }
                }
            }
        }
    }

    init {
        initGatewayHandlers(genesisClient, this)
    }

    fun disconnect() {
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
                channel.addMessages(msg.messages)
                break
            }
        }
        return
    }
}