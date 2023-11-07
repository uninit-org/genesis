package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import xyz.genesisapp.discord.api.types.Asset
import xyz.genesisapp.discord.api.types.Snowflake

@Serializable
data class User(
    @SerialName("accent_color")
    val accentColor: Int? = null,
    val avatar: Asset? = null,
    @SerialName("avatar_decoration_data")
    val avatarDecorationData: JsonObject? = null,
    val banner: Asset? = null,
    @SerialName("banner_color")
    val bannerColor: String? = null,
    val bio: String? = null,
    val discriminator: String? = null,
    val email: String? = null,
    val flags: UserFlags? = UserFlags(0),
    @SerialName("global_name")
    val globalName: String? = null,
    val id: Snowflake,
    @SerialName("mfa_enabled")
    val mfaEnabled: Boolean = false,
    @SerialName("nsfw_allowed")
    val nsfwAllowed: Boolean = false,
    val phone: String? = null,
    @SerialName("premium_type")
    val premiumType: PremiumType = PremiumType.NONE,
//    @SerialName("premium_usage_flags")
//    val premiumUsageFlags: Int?, // todo: figure out what these are
    @SerialName("purchased_flags")
    val purchasedFlags: Int? = null, // todo: figure out what these are
    val username: String,
    val verified: Boolean = false,
)