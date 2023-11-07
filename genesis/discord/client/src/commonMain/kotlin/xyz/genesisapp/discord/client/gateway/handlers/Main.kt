package xyz.genesisapp.discord.client.gateway.handlers

import io.github.aakira.napier.Napier
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.gateway.GatewayClient

fun initGatewayHandlers(genesisClient: GenesisClient, gateway: GatewayClient) {
    Napier.v("initGatewayHandlers")
    gatewayReadyHandler(genesisClient, gateway)
    gatewayOp1Handler(gateway)
    gatewayMessageCreateHandler(genesisClient, gateway)
}