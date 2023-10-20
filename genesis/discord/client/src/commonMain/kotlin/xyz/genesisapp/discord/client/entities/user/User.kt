package xyz.genesisapp.discord.client.entities.user

import xyz.genesisapp.discord.api.types.user.PremiumType
import xyz.genesisapp.discord.api.types.user.UserFlags

open class User(
    var id: String = "",
    var username: String = "",
    var avatar: String? = null,
    var discriminator: String = "0",
    var publicFlags: UserFlags = UserFlags(0),
    var flags: UserFlags? = null,
    var banner: String? = null,
    var accentColor: Int? = null,
    var globalName: String? = null,
    var bannerColor: Int? = null,
    var premiumType: PremiumType? = null,
    var bio: String? = null
) {

}