package xyz.genesisapp.discord.client.entities.guild

import xyz.genesisapp.common.fytix.Err
import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.common.fytix.Ok
import xyz.genesisapp.discord.api.domain.ApiMessage
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
    var children: MutableList<Snowflake> = mutableListOf()
) : EventEmitter() {
    val messages = mutableListOf<Message>()

    fun addMessage(apiMessage: ApiMessage) {
        val message = Message.fromApiMessage(apiMessage, genesisClient)
        if (messages.find { it.id == message.id } == null) {
            messages.add(message)
            sort()
            emit("MESSAGE_CREATE", message)
        }
    }

    fun addMessages(messages: List<ApiMessage>) = messages.forEach { addMessage(it) }

    val guild: Guild
        get() = genesisClient.guilds[guildId]!!

    internal fun sort() {
        messages.sortBy { it.id }
    }

    suspend fun fetchMessages(limit: Int = 50, after: Snowflake? = null): List<Message>? {
        return when (val res = genesisClient.rest.getMessages(id, limit, after)) {
            is Ok -> {
                val newMessages = res.value.map { Message.fromApiMessage(it, genesisClient) }
                messages.addAll(newMessages)
                sort()
                emit("MESSAGE_CREATE_BULK", newMessages)
                newMessages
            }

            is Err -> {
                null
            }
        }
    }

    suspend fun sendMessage(content: String): Message? {
        val res = genesisClient.rest.sendMessage(id, ApiMessage(content = content))
        return when (res) {
            is Ok -> {
                Message.fromApiMessage(res.value, genesisClient)
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
            type = apiChannel.type!!
        )
    }
}