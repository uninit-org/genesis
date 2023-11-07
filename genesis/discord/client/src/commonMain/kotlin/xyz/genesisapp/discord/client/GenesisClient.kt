package xyz.genesisapp.discord.client

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.engine.*
import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.discord.api.annotations.ExperimentalDiscordApi
import xyz.genesisapp.discord.api.domain.user.DomainMe
import xyz.genesisapp.discord.api.domain.user.UserSettings
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.entities.guild.Channel
import xyz.genesisapp.discord.client.entities.guild.Guild
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.discord.client.gateway.GatewayClient
import xyz.genesisapp.discord.client.rest.RestClient
import xyz.genesisapp.discord.entities.guild.GuildMember

@OptIn(ExperimentalDiscordApi::class)
class GenesisClient(
    httpClientEngineFactory: HttpClientEngineFactory<*>,
) {
    lateinit var user: DomainMe // ClientUser

    var logLevel: LogLevel = LogLevel.INFO

    val rest: RestClient = RestClient(httpClientEngineFactory, genesisClient = this)

    val gateway: GatewayClient = GatewayClient(httpClientEngineFactory, genesisClient = this)

    val events: EventEmitter = EventEmitter()

    var guilds: MutableMap<Snowflake, Guild?> = mutableMapOf()
    var channels: MutableMap<Snowflake, Channel> = mutableMapOf()
    var guildMembers: MutableMap<Snowflake, GuildMember> = mutableMapOf()
    var userSettings: UserSettings? = null

//    class ClientUser(
//        var token: String = "",
//        var email: String? = null,
//        var nsfwAllowed: Boolean = true,
//    ) : User()

    init {
//        var packs = PremiumType.NITRO.ALLOWED_EMOJI_PACKS
        Napier.base(DebugAntilog())
    }
}