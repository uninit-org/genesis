package xyz.genesisapp.discord.client.gateway.types

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class GatewayEvent(
    val op: Int,
    @Contextual
    val d: Any,
    val s: Int?,
    val t: String?,
)