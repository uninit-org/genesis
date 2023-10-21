package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.Serializable

@Serializable
data class ApiUser(
    var id: String,
    var username: String,
    var avatar: String? = null,
    var discriminator: String = "0",
    var publicFlags: UserFlags = UserFlags(0),
    var banner: String? = null,
    var accentColor: Int? = null,
    var globalName: String? = null,
    var bannerColor: Int? = null,
    var premiumType: PremiumType? = null,
    var bio: String? = null,
)

@Serializable
data class ApiClientUser(
    var id: String,
    var username: String,
    var avatar: String? = null,
    var discriminator: String = "0",
    var publicFlags: UserFlags = UserFlags(0),
    var flags: UserFlags? = null,
    var banner: String? = null,
    var accentColor: Int? = null,
    var globalName: String? = null,
    var bannerColor: Int? = null,
    var premiumType: PremiumType? = null,
    var email: String? = null,
    var nsfwAllowed: Boolean? = null,
    var bio: String? = null,
)