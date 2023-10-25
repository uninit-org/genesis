package xyz.genesisapp.discord.client.gateway

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.discord.client.gateway.types.GatewayEvent

class GatewayClient(
    engineFactory: HttpClientEngineFactory<*>,
) : EventEmitter() {
    val http = HttpClient(engineFactory) {
        install(WebSockets)
    }

    val Json = Json {
        classDiscriminator = "T"
    }

//    object Serializer : JsonContentPolymorphicSerializer<GatewayEvent>(GatewayEvent::class) {
//        override fun selectDeserializer(content: JsonElement) = {
//            // opCode is an int
//            val opcode = content.jsonObject["op"]!!.primitive.int
//            when (opcode) {
//                10 -> GatewayEvent.serializer()
//                else -> GatewayEvent.serializer()
//            }
//        }
//    }

    var websocket: DefaultClientWebSocketSession? = null


    suspend inline fun <reified T> send(data: T) {
        if (websocket?.isActive == true) {
            val text = if (data is String) {
                data
            } else {
                Json.encodeToString(data)
            }
            val frame = Frame.Text(text)
            websocket!!.send(frame)
        }
    }
    
    suspend fun connect() {
        http.webSocket("wss://gateway.discord.gg/?v=9&encoding=json") {
            while (true) {
                val othersMessage = incoming.receive() as? Frame.Text ?: continue
                val json = Json.decodeFromString<GatewayEvent>(othersMessage.readText())
                println(json)
            }
        }
    }

    suspend fun disconnect() {
        websocket?.close()
    }
}