package xyz.genesisapp.discord.client.gateway

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
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
import xyz.genesisapp.discord.client.gateway.handlers.initGatewayHandlers
import xyz.genesisapp.discord.client.gateway.serializers.GatewaySerializer
import xyz.genesisapp.discord.client.gateway.types.GatewayEvent
import xyz.genesisapp.discord.client.gateway.types.events.opCode.GatewayIdentify

class GatewayClient(
    engineFactory: HttpClientEngineFactory<*>,
    genesisClient: GenesisClient
) : EventEmitter() {
    val http = HttpClient(engineFactory) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
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
        if (websocket?.isActive == true) {
            val text = if (data is String) {
                data
            } else {
                JsonClient.encodeToString(data)
            }
            val frame = Frame.Text(text)
            scope.launch {
                websocket!!.send(frame)
            }
        }
    }

    fun parseMessage(message: String) = JsonClient.decodeFromString(GatewaySerializer, message)

    fun connect(token: String, intents: Int, os: String = "genesis") {
        if (websocket?.isActive == true) return
        scope.launch {
            http.webSocket("wss://gateway.discord.gg/?v=9&encoding=json") {
                websocket = this
                val identifyPacket = GatewayEvent(
                    2, null, null, GatewayIdentify(
                        token = token,
                        properties = GatewayIdentify.Properties(
                            os = os
                        ),
                        intents = intents,
                    )
                )
                send(identifyPacket)
                while (true) {
                    val othersMessage = incoming.receive() as? Frame.Text ?: continue
                    emit("raw", othersMessage.readText())
                    try {
                        val text = othersMessage.readText()
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
                        for (blacklisted in blacklist) {
                            if (e.message?.contains(blacklisted) == true) {
                                println(e)
                                return@webSocket
                            }
                        }
                        throw e
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
}