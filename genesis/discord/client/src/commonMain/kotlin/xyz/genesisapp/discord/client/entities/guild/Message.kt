package xyz.genesisapp.discord.client.entities.guild

import xyz.genesisapp.common.getTimeInMillis
import xyz.genesisapp.discord.api.domain.ApiMessage
import xyz.genesisapp.discord.api.domain.UtcDateTime
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

    var embeds: List<Embed> = listOf(),
    var attachments: List<Attachment> = listOf(),
) {

    companion object {
        fun fromApiMessage(apiMessage: ApiMessage, genesisClient: GenesisClient) = Message(
            genesisClient,
            id = apiMessage.id ?: apiMessage.nonce!!,
            channelId = apiMessage.channelId!!,
            content = apiMessage.content ?: "",
            author = User.fromApiUser(apiMessage.author!!, genesisClient),
            nonce = apiMessage.nonce ?: (getTimeInMillis() + UtcDateTime.DISCORD_EPOCH * 1000000),
            embeds = apiMessage.embeds?.map { Embed.fromApiMessageEmbed(it) } ?: listOf(),
            attachments = apiMessage.attachments?.map { Attachment.fromApiMessageAttachment(it) }
                ?: listOf(),
        )
    }
}