package xyz.genesisapp.discord.client.gateway.entities

import kotlinx.serialization.Serializable

interface GenericGatewayEvent {
    val op: Int
    val s: Int?
    val t: String?
}