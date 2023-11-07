package xyz.genesisapp.discord.client.gateway.types.events.opCode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GatewayRequestMessages(
    @SerialName("guild_id")
    val guildId: String,
    @SerialName("channel_ids")
    val channelIds: List<String>,
)