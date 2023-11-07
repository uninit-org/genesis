package xyz.genesisapp.discord.client.gateway.types.events.opCode

import kotlinx.serialization.Serializable
import xyz.genesisapp.discord.client.gateway.types.SuperProperties
import xyz.genesisapp.discord.client.getSuperProperties


@Serializable
data class GatewayIdentify(
    val token: String,
    val properties: SuperProperties = getSuperProperties(),
    val compress: Boolean = false,
    // TODO: PRESENCE UPDATE
) {
}