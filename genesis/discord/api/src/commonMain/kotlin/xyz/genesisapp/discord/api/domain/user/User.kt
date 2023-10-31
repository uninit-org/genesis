package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class User(
    @SerialName("accent_color")
    val accentColor: Int?,
    val avatar: String?,
    @SerialName("avatar_decoration_data")
    val avatarDecorationData: JsonElement? = null,
    val banner: String? = null,
    @SerialName("banner_color")
    val bannerColor: String? = null,
    val bio: String? = null,
    val discriminator: String? = null,
    val email: String? = null,
    val flags: UserFlags,
    @SerialName("global_name")
    val globalName: String,
    val id: String,
    @SerialName("mfa_enabled")
    val mfaEnabled: Boolean,
    @SerialName("nsfw_allowed")
    val nsfwAllowed: Boolean,
    val phone: String? = null,
    @SerialName("premium_type")
    val premiumType: PremiumType,
//    @SerialName("premium_usage_flags")
//    val premiumUsageFlags: Int?, // todo: figure out what these are
    @SerialName("purchased_flags")
    val purchasedFlags: Int, // todo: figure out what these are
    val username: String,
    val verified: Boolean,
)