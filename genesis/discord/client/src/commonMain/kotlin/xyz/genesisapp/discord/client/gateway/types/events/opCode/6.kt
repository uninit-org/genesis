package xyz.genesisapp.discord.client.gateway.types.events.opCode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GatewayResume(
    var token: String,
    @SerialName("session_id")
    var sessionId: String,
    var seq: Int
)