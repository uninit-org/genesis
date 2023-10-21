package xyz.genesisapp.discord.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthMiniUserSettings(
    val locale: String,
    val theme: String,
)
