package xyz.genesisapp.discord.client.entities.guild

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import xyz.genesisapp.common.fytix.Err
import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.common.fytix.Ok
import xyz.genesisapp.common.getTimeInMillis
import xyz.genesisapp.discord.api.domain.ApiMessage
import xyz.genesisapp.discord.api.domain.UtcDateTime
import xyz.genesisapp.discord.api.types.Asset
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.entities.guild.ApiChannel
import xyz.genesisapp.discord.entities.guild.ChannelType

class Channel(
    val genesisClient: GenesisClient,
    var id: Snowflake,
    var guildId: Snowflake,
    var position: Int,
    var name: String,
    var nsfw: Boolean,
    var parentId: Snowflake? = null,
    var type: ChannelType,
    var children: MutableList<Snowflake> = mutableListOf(),
    var isCollapsed: MutableState<Boolean> = mutableStateOf(false),
    val recipients: MutableList<User> = mutableListOf(),
    val icon: Asset? = null,
    val lastMessageId: Snowflake? = null,
) : EventEmitter() {
    val messages = mutableStateListOf<Message>()

    private fun addMessage(message: Message, isBulk: Boolean = false): Message {
        if (messages.find { it.id == message.id } == null) {
            messages.add(message)
            sort()
            if (!isBulk) emit("MESSAGE_CREATE", message)
        }
        return message
    }

    fun addApiMessages(apiMessages: List<ApiMessage>): List<Message> =
        addMessages(apiMessages.map { Message.fromApiMessage(it, genesisClient) })

    fun addMessages(messages: List<Message>): List<Message> {
        val added = mutableListOf<Message>()
        messages.forEach { added.add(addMessage(it, true)) }
        emit("MESSAGE_CREATE_BULK", added)
        return added
    }

    fun deleteMessage(message: Message) = deleteMessage(message.id)

    fun deleteMessage(apiMessage: ApiMessage) = deleteMessage(apiMessage.id!!)

    fun deleteMessage(id: Snowflake) {
        val message = messages.find { it.id == id }
        if (message != null) {
            messages.remove(message)
            emit("MESSAGE_DELETE", message.id)
        }
    }

    val guild: Guild
        get() = genesisClient.guilds[guildId]!!

    internal fun sort() {
        messages.sortBy { it.id }
    }

    suspend fun fetchMessages(limit: Int = 50, after: Snowflake? = null): List<Message>? {
        return when (val res = genesisClient.rest.getMessages(id, limit, after)) {
            is Ok -> {
                return addApiMessages(res.value)
            }

            is Err -> {
                null
            }
        }
    }

    suspend fun sendMessage(content: String): Message? {
        val apiMessage = ApiMessage(
            content = content,
            channelId = id,
            nonce = getTimeInMillis() + UtcDateTime.DISCORD_EPOCH * 1000000
        )
        println(apiMessage.nonce)
        println(messages.last().id)
        println(apiMessage.nonce!! > messages.last().id)
        var message = Message.fromApiMessage(apiMessage, genesisClient)
        message.isSent = false
        message.author = genesisClient.normalUser
        addMessage(message)
        delay(3000L)
        return when (val res = genesisClient.rest.sendMessage(id, apiMessage)) {
            is Ok -> {
                messages.remove(message)
                message = Message.fromApiMessage(res.value, genesisClient)
                addMessage(message)
                message
            }

            is Err -> {
                println(res.error)
                null
            }
        }
    }

    companion object {
        fun fromApiChannel(
            apiChannel: ApiChannel,
            guildId: Snowflake,
            genesisClient: GenesisClient
        ): Channel = Channel(
            genesisClient,
            id = apiChannel.id,
            guildId = guildId,
            position = apiChannel.position!!,
            name = apiChannel.name!!,
            nsfw = apiChannel.nsfw ?: false,
            parentId = apiChannel.parentId,
            type = apiChannel.type!!,
            recipients = apiChannel.recipients?.map { User.fromApiUser(it, genesisClient) }
                ?.toMutableList() ?: mutableListOf(),
            icon = apiChannel.icon,
            lastMessageId = apiChannel.lastMessageId
        )
    }
}