package xyz.genesisapp.discord.entities.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//name	string	guild name
//region	?string	guild voice region id (deprecated)
//verification_level	?integer	verification level
//default_message_notifications	?integer	default message notification level
//explicit_content_filter	?integer	explicit content filter level
//afk_channel_id	?snowflake	id for afk channel
//afk_timeout	integer	afk timeout in seconds, can be set to: 60, 300, 900, 1800, 3600
//icon	?image data	base64 1024x1024 png/jpeg/gif image for the guild icon (can be animated gif when the server has the ANIMATED_ICON feature)
//owner_id	snowflake	user id to transfer guild ownership to (must be owner)
//splash	?image data	base64 16:9 png/jpeg image for the guild splash (when the server has the INVITE_SPLASH feature)
//discovery_splash	?image data	base64 16:9 png/jpeg image for the guild discovery splash (when the server has the DISCOVERABLE feature)
//banner	?image data	base64 16:9 png/jpeg image for the guild banner (when the server has the BANNER feature; can be animated gif when the server has the ANIMATED_BANNER feature)
//system_channel_id	?snowflake	the id of the channel where guild notices such as welcome messages and boost events are posted
//system_channel_flags	integer	system channel flags
//rules_channel_id	?snowflake	the id of the channel where Community guilds display rules and/or guidelines
//public_updates_channel_id	?snowflake	the id of the channel where admins and moderators of Community guilds receive notices from Discord
//preferred_locale	?string	the preferred locale of a Community guild used in server discovery and notices from Discord; defaults to "en-US"
//features	array of guild feature strings	enabled guild features
//description	?string	the description for the guild
//premium_progress_bar_enabled	boolean	whether the guild's boost progress bar should be enabled
//safety_alerts_channel_id
@Serializable
class Guild(
    val id: String,
    val unavailable: Boolean = false,
    var name: String,
    var region: String? = null,
    @SerialName("verification_level")
    var verificationLevel: Int? = null,
    @SerialName("default_message_notifications")
    var defaultMessageNotifications: Int? = null,
    @SerialName("explicit_content_filter")
    var explicitContentFilter: Int? = null,
    @SerialName("afk_channel_id")
    var afkChannelId: String? = null,
    @SerialName("afk_timeout")
    var afkTimeout: Int? = null,
    var icon: String? = null,
    @SerialName("owner_id")
    var ownerId: String? = null,
    var splash: String? = null,
    @SerialName("discovery_splash")
    var discoverySplash: String? = null,
    var banner: String? = null,
    @SerialName("system_channel_id")
    var systemChannelId: String? = null,
    @SerialName("system_channel_flags")
    var systemChannelFlags: Int? = null,
    @SerialName("rules_channel_id")
    var rulesChannelId: String? = null,
    @SerialName("public_updates_channel_id")
    var publicUpdatesChannelId: String? = null,
    @SerialName("preferred_locale")
    var preferredLocale: String? = null,
    var features: List<String>? = null,
    var description: String? = null,
    @SerialName("premium_progress_bar_enabled")
    var premiumProgressBarEnabled: Boolean,
    @SerialName("safety_alerts_channel_id")
    var safetyAlertsChannelId: String? = null,
) {
}

@Serializable
class GuildUnavailable(
    val id: String,
    val unavailable: Boolean = true,
) {
}