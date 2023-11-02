package xyz.genesisapp.discord.client

import io.ktor.client.engine.*
import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.discord.api.annotations.ExperimentalDiscordApi
import xyz.genesisapp.discord.api.domain.user.DomainMe
import xyz.genesisapp.discord.api.domain.user.UserSettings
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.gateway.GatewayClient
import xyz.genesisapp.discord.client.rest.RestClient
import xyz.genesisapp.discord.entities.guild.Guild

@OptIn(ExperimentalDiscordApi::class)
class GenesisClient(httpClientEngineFactory: HttpClientEngineFactory<*>) {
    lateinit var user: DomainMe // ClientUser

    val rest: RestClient = RestClient(httpClientEngineFactory)

    val gateway: GatewayClient = GatewayClient(httpClientEngineFactory, this)

    val events: EventEmitter = EventEmitter()

    var guilds: MutableMap<Snowflake, Guild?> = mutableMapOf()
    var userSettings: UserSettings? = null

//    class ClientUser(
//        var token: String = "",
//        var email: String? = null,
//        var nsfwAllowed: Boolean = true,
//    ) : User()

//    init {
//        var packs = PremiumType.NITRO.ALLOWED_EMOJI_PACKS
//    }
}