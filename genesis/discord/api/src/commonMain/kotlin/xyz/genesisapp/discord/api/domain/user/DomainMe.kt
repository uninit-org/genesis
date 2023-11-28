package uninit.genesis.discord.api.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import uninit.genesis.discord.api.types.Asset
import uninit.genesis.discord.api.types.Snowflake

@Serializable
data class DomainMe(
    @SerialName("accent_color")
    val accentColor: Int?,
    @SerialName("authenticator_types")
    val authenticatorTypes: List<Int>,
    val avatar: Asset?,
    @SerialName("avatar_decoration_data")
    val avatarDecorationData: JsonObject? = null,
    val banner: Asset? = null,
    @SerialName("banner_color")
    val bannerColor: String? = null,
    val bio: String? = null,
    val discriminator: String? = null,
    val email: String? = null,
    val flags: UserFlags,
    @SerialName("global_name")
    val globalName: String,
    val id: Snowflake,
    @SerialName("linked_users")
    val linkedUsers: JsonArray,
    val locale: String,
    @SerialName("mfa_enabled")
    val mfaEnabled: Boolean,
    @SerialName("nsfw_allowed")
    val nsfwAllowed: Boolean,
    val phone: String? = null,
    @SerialName("premium_type")
    val premiumType: PremiumType,
//    @SerialName("premium_usage_flags")
//    val premiumUsageFlags: Int?, // todo: figure out what these are
    @SerialName("public_flags")
    val publicFlags: UserFlags,
    @SerialName("purchased_flags")
    val purchasedFlags: Int? = null, // todo: figure out what these are
    val username: String,
    val verified: Boolean,
)