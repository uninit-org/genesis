package xyz.genesisapp.discord.client.gateway.handlers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.gateway.GatewayClient
import xyz.genesisapp.discord.entities.guild.Guild

fun gatewayGuildCreateHandler(genesisClient: GenesisClient, gateway: GatewayClient) {
    val scope = CoroutineScope(Dispatchers.IO)
    gateway.on<Guild>("GUILD_CREATE") {
        println("guild create")
        genesisClient.guilds[it.id] = it

        var isntReady = false
        genesisClient.guilds.values.forEach { guild ->
            if (guild == null) isntReady = true
        }

        if (!isntReady) {
            genesisClient.events.emit("READY", "")
        }
    }
}