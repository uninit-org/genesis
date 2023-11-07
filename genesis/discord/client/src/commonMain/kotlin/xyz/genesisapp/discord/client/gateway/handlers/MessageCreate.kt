package xyz.genesisapp.discord.client.gateway.handlers

import xyz.genesisapp.discord.api.domain.ApiMessage
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.entities.guild.Message
import xyz.genesisapp.discord.client.gateway.GatewayClient

fun gatewayMessageCreateHandler(genesisClient: GenesisClient, gateway: GatewayClient) {
    gateway.on<ApiMessage>("MESSAGE_CREATE") { msg ->
        if (!genesisClient.channels.containsKey(msg.channelId)) return@on
        val channel = genesisClient.channels[msg.channelId]!!
        if (!genesisClient.guilds.containsKey(channel.guildId)) return@on
        val message = Message.fromApiMessage(msg, genesisClient)
        channel.messages.add(message)
        channel.sort()
        genesisClient.events.emit("MESSAGE_CREATE", message)
        channel.emit("MESSAGE_CREATE", message)
    }
}