package uninit.genesis.discord.api.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthMfaSuccess(
    val token: String,
    @SerialName("user_settings")
    val userSettings: AuthMiniUserSettings,
)
