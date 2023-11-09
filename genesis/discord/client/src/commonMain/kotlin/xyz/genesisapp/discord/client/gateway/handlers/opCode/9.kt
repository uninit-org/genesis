package xyz.genesisapp.discord.client.gateway.handlers

import io.github.aakira.napier.Napier
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.discord.client.gateway.GatewayClient

fun gatewayOp9Handler(genesisClient: GenesisClient, gateway: GatewayClient) {
    gateway.on<Boolean>("9") {
        if (it) return@on
        if (genesisClient.logLevel >= LogLevel.ERROR) Napier.e("Gateway session invalidated")
        gateway.disconnect()
        gateway.connect()
    }
}