package xyz.genesisapp.discord.client.gateway.handlers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.gateway.GatewayClient
import xyz.genesisapp.discord.client.gateway.types.events.Ready

fun gatewayReadyHandler(genesisClient: GenesisClient, gateway: GatewayClient) {
    val scope = CoroutineScope(Dispatchers.IO)
    gateway.on<Ready>("READY") { ready ->
        gateway.sessionId = ready.sessionId
        genesisClient.userSettings = ready.userSettings
        genesisClient.guilds = ready.guilds.associateBy { it.id }.toMutableMap()

        scope.launch {
            val me = genesisClient.rest.getDomainMe()
            if (me.isOk()) genesisClient.user = me.getOrNull()!!
            genesisClient.events.emit("READY", "")
        }
    }
}