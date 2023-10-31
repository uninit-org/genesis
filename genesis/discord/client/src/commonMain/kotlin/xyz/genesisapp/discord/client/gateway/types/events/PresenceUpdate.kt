package xyz.genesisapp.discord.client.gateway.types.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.genesisapp.discord.api.domain.user.ClientStatus
import xyz.genesisapp.discord.api.domain.user.Status

@Serializable
data class PresenceUpdate(
    // TODO: Activities
    // TODO: broadcast
    val client_status: ClientStatus,
    @SerialName("guild_id")
    val guildId: String,
    val status: Status
)