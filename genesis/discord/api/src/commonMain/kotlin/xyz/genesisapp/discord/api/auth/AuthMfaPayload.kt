package uninit.genesis.discord.api.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * ### The payload for the MFA login flow.
 *
 * ## How to use
 *
 * This should be POSTed to ${BASE_URL}/api/${API_VER}/auth/mfa/totp
 *
 * ## Responses
 * This will return one of the following:
 *
 * - [ApiError] If the code is invalid
 * - [AuthMfaSuccess] If the code is valid
 */
@Serializable
data class AuthMfaPayload(

    /**
     * The MFA code, typically 6 digits for an OTP (via Authenticator App) and 8 chars for a backup code.
     */
    val code: String,

    /**
     * The session token for the MFA login flow.
     */
    val ticket: String,

    /**
     * The source of the login request. (Undocumented, optional)
     */
    @SerialName("login_source")
    val loginSource: String? = null,

    /**
     * The gift code sku id to redeem. (Undocumented, optional)
     */
    @SerialName("gift_code_sku_id")
    val giftCodeId: String? = null,
)
