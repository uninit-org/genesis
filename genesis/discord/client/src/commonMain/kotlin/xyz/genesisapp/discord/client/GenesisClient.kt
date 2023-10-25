package xyz.genesisapp.discord.client

import io.ktor.client.engine.*
import xyz.genesisapp.discord.api.annotations.ExperimentalDiscordApi
import xyz.genesisapp.discord.client.gateway.GatewayClient
import xyz.genesisapp.discord.client.rest.RestClient

@OptIn(ExperimentalDiscordApi::class)
class GenesisClient(httpClientEngineFactory: HttpClientEngineFactory<*>) {
//    lateinit var user: ClientUser

    val rest: RestClient = RestClient(httpClientEngineFactory)

    val gateway: GatewayClient = GatewayClient(httpClientEngineFactory)


//    class ClientUser(
//        var token: String = "",
//        var email: String? = null,
//        var nsfwAllowed: Boolean = true,
//    ) : User()

//    init {
//        var packs = PremiumType.NITRO.ALLOWED_EMOJI_PACKS
//    }
}