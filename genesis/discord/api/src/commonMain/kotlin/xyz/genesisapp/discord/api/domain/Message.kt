package xyz.genesisapp.discord.api.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.genesisapp.discord.api.domain.user.User
import xyz.genesisapp.discord.api.types.Snowflake

@Serializable
data class ApiMessage(
    var id: Snowflake? = null,

    var content: String? = null,

    @SerialName("channel_id")
    var channelId: Snowflake? = null,

    var author: User? = null,
)