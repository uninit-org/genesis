package xyz.genesisapp.discord.client.entities.guild

import xyz.genesisapp.common.getTimeInMillis
import xyz.genesisapp.discord.api.domain.DomainMessage
import xyz.genesisapp.discord.api.domain.UtcDateTime
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.GenesisClient

class Message(
    val genesisClient: GenesisClient,
    var id: Snowflake,
    var channelId: Snowflake,
    var content: String = "",
    var isSent: Boolean = true,
    var nonce: Long = getTimeInMillis() + UtcDateTime.DISCORD_EPOCH * 1000000,
    var authorId: Snowflake,
    var embeds: List<Embed> = listOf(),
    var attachments: List<Attachment> = listOf(),
) {

    val author: User
        get() = genesisClient.users[authorId]!!

    companion object {
        fun fromApiMessage(domainMessage: DomainMessage, genesisClient: GenesisClient): Message {
            domainMessage.author?.let {
                if (!genesisClient.users.containsKey(it.id)) genesisClient.users[it.id] =
                    User.fromApiUser(it, genesisClient)
            }

            return Message(
                genesisClient,
                id = domainMessage.id ?: domainMessage.nonce!!,
                channelId = domainMessage.channelId!!,
                content = domainMessage.content ?: "",
                authorId = domainMessage.author!!.id,
                nonce = domainMessage.nonce
                    ?: (getTimeInMillis() + UtcDateTime.DISCORD_EPOCH * 1000000),
                embeds = domainMessage.embeds?.map { Embed.fromApiMessageEmbed(it) } ?: listOf(),
                attachments = domainMessage.attachments?.map {
                    Attachment.fromApiMessageAttachment(
                        it
                    )
                }
                    ?: listOf(),
            )
        }
    }
}