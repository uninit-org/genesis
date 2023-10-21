package xyz.genesisapp.discord.api.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AuthStartMfaFlow(
    @SerialName("user_id")
    val userId: String,
    val mfa: Boolean,
    val sms: Boolean,
    val ticket: String,
    val backup: Boolean,
    val totp: Boolean,
    /**
     * I have no fucking idea what this is
     */
    val webauthn: JsonElement? = null,
)
