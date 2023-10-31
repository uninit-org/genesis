package xyz.genesisapp.discord.client.gateway.types.events.opCode

import kotlinx.serialization.Serializable


@Serializable
data class GatewayIdentify(
    val token: String,
    val properties: Properties,
    val compress: Boolean = false,
    // TODO: PRESENCE UPDATE
    val intents: Int,
) {
    @Serializable
    data class Properties(
        val os: String,
        val browser: String = "genesis",
        val device: String = "genesis"
    )
}