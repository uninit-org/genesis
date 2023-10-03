package cat.jai.libdiscord.api.user

class User(
    val analyticsToken: String = "",
    val approxGuildCount: Int = 0,
    val avatar: String,
    val banner: String,
    val bannerColor: String,
    val bio: String,
    val bot: Boolean,
    val discriminator: String? = null,
    val email: String? = null,
    val flags: Int,
    val id: Long,
    val locale: String,
    val member: Any, // TODO: Implement GuildMember class
    val mfaEnabled: Boolean,
    val nsfwAllowed: Boolean,
    val phone: String?,
    val premiumType: Int, // TODO: PremiumType enum
    val publicFlags: Int,
    val system: Boolean,
    val token: String,
    val username: String,
    val verified: Boolean
) {
}