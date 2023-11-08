package xyz.genesisapp.discord.client.gateway.handlers

import xyz.genesisapp.discord.client.gateway.GatewayClient
import xyz.genesisapp.discord.client.gateway.types.EmptyGatewayEvent
import xyz.genesisapp.discord.client.gateway.types.events.Ready

fun gatewayOp1Handler(gateway: GatewayClient) {
    gateway.on<Ready>("1") {
        gateway.send(EmptyGatewayEvent(11))
    }
}