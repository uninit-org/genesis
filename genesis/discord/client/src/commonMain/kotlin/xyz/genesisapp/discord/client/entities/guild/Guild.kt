package xyz.genesisapp.discord.client.entities.guild

import xyz.genesisapp.discord.api.types.Asset
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.entities.guild.ApiGuild
import xyz.genesisapp.discord.entities.guild.ApiRole
import xyz.genesisapp.discord.entities.guild.GuildMember

class Guild(
    val id: Snowflake,
    var name: String,
    var region: String? = null,

    var verificationLevel: Int? = null,

    var defaultMessageNotifications: Int? = null,

    var explicitContentFilter: Int? = null,

    var afkChannelId: String? = null,

    var afkTimeout: Int? = null,
    var icon: Asset? = null,

    var ownerId: String? = null,
    var splash: Asset? = null,

    var discoverySplash: String? = null,
    var banner: Asset? = null,

    var systemChannelId: String? = null,

    var systemChannelFlags: Int? = null,

    var rulesChannelId: String? = null,

    var publicUpdatesChannelId: String? = null,

    var preferredLocale: String? = null,
    var features: List<String>? = null,
    var description: String? = null,

    var premiumProgressBarEnabled: Boolean,

    var safetyAlertsChannelId: String? = null,
    var members: MutableList<GuildMember>? = mutableListOf(),

    val roles: MutableList<ApiRole> = mutableListOf(),

    val genesisClient: GenesisClient
) {

    val channels: List<Channel>
        get() {
            return genesisClient.channels.values.filter { it.guildId == id }.toList()
        }

    companion object {
        fun fromApiGuild(apiGuild: ApiGuild, genesisClient: GenesisClient) = Guild(
            id = if (apiGuild.id === "@me") 0L else apiGuild.id.toLong(),
            name = apiGuild.name,
            region = apiGuild.region,

            verificationLevel = apiGuild.verificationLevel,

            defaultMessageNotifications = apiGuild.defaultMessageNotifications,

            explicitContentFilter = apiGuild.explicitContentFilter,

            afkChannelId = apiGuild.afkChannelId,

            afkTimeout = apiGuild.afkTimeout,
            icon = apiGuild.icon,

            ownerId = apiGuild.ownerId,
            splash = apiGuild.splash,

            discoverySplash = apiGuild.discoverySplash,
            banner = apiGuild.banner,

            systemChannelId = apiGuild.systemChannelId,

            systemChannelFlags = apiGuild.systemChannelFlags,

            rulesChannelId = apiGuild.rulesChannelId,

            publicUpdatesChannelId = apiGuild.publicUpdatesChannelId,

            preferredLocale = apiGuild.preferredLocale,
            features = apiGuild.features,
            description = apiGuild.description,

            premiumProgressBarEnabled = apiGuild.premiumProgressBarEnabled,

            safetyAlertsChannelId = apiGuild.safetyAlertsChannelId,
            roles = apiGuild.roles?.toMutableList() ?: mutableListOf(),
            genesisClient = genesisClient
        )
    }
}