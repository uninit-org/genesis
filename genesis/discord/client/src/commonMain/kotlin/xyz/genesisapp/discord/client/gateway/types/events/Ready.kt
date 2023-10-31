package xyz.genesisapp.discord.client.gateway.types.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.genesisapp.discord.api.domain.user.UserSettings
import xyz.genesisapp.discord.entities.guild.Guild

@Serializable
data class Ready(
//    val user: DomainMe, Normal User
    @SerialName("session_id")
    val sessionId: String,
    @SerialName("resume_gateway_url")
    val resumeGatewayUrl: String,
    val shard: List<Int>? = null,
    @SerialName("user_settings")
    val userSettings: UserSettings? = null,
    val guilds: List<Guild>,
)
