package xyz.genesisapp.discord.client.gateway.types.events.opCode

import kotlinx.serialization.Serializable

@Serializable
data class GatewayHello(
    val heartbeat_interval: Int,
    val _trace: List<String>,
)