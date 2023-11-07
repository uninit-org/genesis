package xyz.genesisapp.discord.client.gateway.types.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.genesisapp.discord.api.domain.ApiMessage
import xyz.genesisapp.discord.api.types.Snowflake

@Serializable
data class LastMessages(
    @SerialName("guild_id")
    val guildId: Snowflake,
    val messages: List<ApiMessage>,
)