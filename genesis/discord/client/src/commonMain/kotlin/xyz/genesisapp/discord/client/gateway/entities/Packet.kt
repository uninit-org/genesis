package xyz.genesisapp.discord.client.gateway.entities

import xyz.genesisapp.discord.client.gateway.entities.events.GatewayIdentify
import xyz.genesisapp.discord.client.gateway.entities.events.GatewayResume

object Packet {
    fun resume(token: String, sessionId: String, seq: Int) = GatewayEvent(op = 6, d = GatewayResume(token, sessionId, seq))
    fun identify(token: String) = GatewayEvent(op = 2, d = GatewayIdentify(token))
}