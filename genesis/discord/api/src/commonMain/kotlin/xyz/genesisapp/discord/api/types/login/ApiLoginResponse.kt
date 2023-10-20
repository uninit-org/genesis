package xyz.genesisapp.discord.api.types.login

import kotlinx.serialization.Serializable

@Serializable
data class ApiLoginResponse(
    val captcha_key: List<String>? = null,
    val captcha_service: String? = null,
    val captcha_sitekey: String? = null,
    val ticket: String? = null
)