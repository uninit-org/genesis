package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonArray

data class DomainUserProfile(
    val user: DomainUserPart,

    @SerialName("connected_accounts")
    val connectedAccounts: List<DomainUserProfileConnectedAccount>,

    @SerialName("premium_since")
    val premiumSince: String,

    @SerialName("premium_type")
    val premiumType: Long,

    @SerialName("premium_guild_since")
    val premiumGuildSince: String,

    @SerialName("profile_themes_experiment_bucket")
    val profileThemesExperimentBucket: Long,

    @SerialName("user_profile")
    val userProfile: DomainUserProfilePart,

    val badges: List<DomainUserBadge>,

    @SerialName("guild_badges")
    val guildBadges: JsonArray,

    @SerialName("mutual_guilds")
    val mutualGuilds: List<DomainUserProfileMutualGuild>? = null,

    @SerialName("legacy_username")
    val legacyUsername: String? = null
)
