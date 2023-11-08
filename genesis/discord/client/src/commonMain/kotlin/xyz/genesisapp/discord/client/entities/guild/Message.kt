package xyz.genesisapp.discord.client.entities.guild

import xyz.genesisapp.common.getTimeInMillis
import xyz.genesisapp.discord.api.domain.ApiMessage
import xyz.genesisapp.discord.api.domain.UtcDateTime
import xyz.genesisapp.discord.api.domain.user.User
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.GenesisClient

class Message(
    val genesisClient: GenesisClient,
    var id: Snowflake,
    var channelId: Snowflake,
    var content: String = "",
    var author: User,
    var isSent: Boolean = true,
    var nonce: Long = getTimeInMillis() + UtcDateTime.DISCORD_EPOCH * 1000000,
) {

    companion object {
        fun fromApiMessage(apiMessage: ApiMessage, genesisClient: GenesisClient) = Message(
            genesisClient,
            id = apiMessage.id ?: apiMessage.nonce!!,
            channelId = apiMessage.channelId!!,
            content = apiMessage.content ?: "",
            author = apiMessage.author!!,
            nonce = apiMessage.nonce ?: (getTimeInMillis() + UtcDateTime.DISCORD_EPOCH * 1000000),
        )
    }
}