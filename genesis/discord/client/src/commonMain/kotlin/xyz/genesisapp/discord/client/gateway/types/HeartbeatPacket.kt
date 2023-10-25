package xyz.genesisapp.discord.client.gateway.types

import kotlinx.serialization.Serializable

@Serializable
data class HeartbeatPacket(
    val heartbeat_interval: Int,
)