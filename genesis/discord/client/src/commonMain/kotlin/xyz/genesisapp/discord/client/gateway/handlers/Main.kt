package xyz.genesisapp.discord.client.gateway.handlers

import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.gateway.GatewayClient

fun initGatewayHandlers(genesisClient: GenesisClient, gateway: GatewayClient) {
    gatewayReadyHandler(genesisClient, gateway)
    gatewayOp1Handler(genesisClient, gateway)
    gatewayGuildCreateHandler(genesisClient, gateway)
}