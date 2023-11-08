package xyz.genesisapp.discord.client.gateway.handlers

import io.github.aakira.napier.Napier
import xyz.genesisapp.discord.client.gateway.GatewayClient
import xyz.genesisapp.discord.client.gateway.types.EmptyGatewayEvent
import xyz.genesisapp.discord.client.gateway.types.events.Ready

fun gatewayOp9Handler(gateway: GatewayClient) {
    gateway.on<Boolean>("9") {
        if (it) return@on
        Napier.e("Gateway session invalidated")
        gateway.disconnect()
        gateway.connect()
    }
}