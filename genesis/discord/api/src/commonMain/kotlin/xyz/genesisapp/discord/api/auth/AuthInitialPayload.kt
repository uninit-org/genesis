package xyz.genesisapp.discord.api.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.genesisapp.discord.api.ApiCaptchaRequirement
import xyz.genesisapp.discord.api.ApiErrorPack

/**
 * ### The payload for the initial login request.
 *
 * ## How to use
 *
 * This should be POSTed to ${BASE_URL}/api/${API_VER}/auth/login
 *
 * ## Responses
 * This will return one of the following:
 *
 * - [ApiErrorPack], typically it'll be either a bad password or a new location detected.
 * - [AuthStartMfaFlow] If MFA is required
 * - [ApiCaptchaRequirement] If a captcha is required
 * - [AuthSfaSuccess] If the login was successful
 */

@Serializable
data class AuthInitialPayload(
    /**
     * The login of the user, typically an email address or phone number.
     */
    val login: String,
    /**
     * The user's password, plaintext.
     */
    val password: String,
    /**
     * The gift code sku id to redeem. (Undocumented, optional)
     */
    @SerialName("gift_code_sku_id")
    val giftCodeId: String? = null,
    /**
     * The source of the login request. (Undocumented, optional)
     */
    @SerialName("login_source")
    val loginSource: String? = null,
    /**
     * Whether to undelete the account. (Undocumented, default false)
     */
    val undelete: Boolean = false
)
