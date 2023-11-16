package xyz.genesisapp.discord.client.gateway.serializers

import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.genesisapp.discord.api.domain.DomainMessage
import xyz.genesisapp.discord.client.gateway.types.EmptyGatewayEvent
import xyz.genesisapp.discord.client.gateway.types.GatewayEvent
import xyz.genesisapp.discord.client.gateway.types.events.LastMessages
import xyz.genesisapp.discord.client.gateway.types.events.Ready
import xyz.genesisapp.discord.client.gateway.entities.events.GatewayHello
import xyz.genesisapp.discord.client.gateway.types.iGatewayEvent

object GatewaySerializer : JsonContentPolymorphicSerializer<iGatewayEvent>(iGatewayEvent::class) {
    override fun selectDeserializer(element: JsonElement) =
        when (element.jsonObject["op"]!!.jsonPrimitive.int) {
            0 -> Opcode0Serializer
            1 -> EmptyGatewayEvent.serializer()
//            9 -> BooleanGatewayEvent.serializer() kotlin doesnt like this :(
            10 -> GatewayEvent.serializer(GatewayHello.serializer())
            else -> error("Unknown opcode: ${element.jsonObject["op"]!!.jsonPrimitive.int}")
        }
}

object Opcode0Serializer : JsonContentPolymorphicSerializer<iGatewayEvent>(iGatewayEvent::class) {
    override fun selectDeserializer(element: JsonElement) =
        when (element.jsonObject["t"]!!.jsonPrimitive.toString().replace("\"", "")) {
            "READY" -> GatewayEvent.serializer(Ready.serializer())
            "MESSAGE_CREATE" -> GatewayEvent.serializer(DomainMessage.serializer())
            "LAST_MESSAGES" -> GatewayEvent.serializer(LastMessages.serializer())
            else -> error("Unknown event: ${element.jsonObject["t"]!!.jsonPrimitive}")
        }
}