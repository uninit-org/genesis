package uninit.genesis.discord.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiCaptchaRequirement(
    val captcha_key: List<String>? = null,
    val captcha_service: String,
    val captcha_sitekey: String,
)