package xyz.genesisapp.discord.api.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthSfaSuccess(
    val token: String,
    @SerialName("user_settings")
    val userSettings: AuthMiniUserSettings,
    @SerialName("user_id")
    val userId: String
)
