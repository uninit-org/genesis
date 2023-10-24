package xyz.genesisapp.discord.client

import org.koin.core.Koin
import xyz.genesisapp.discord.api.annotations.ExperimentalDiscordApi
import xyz.genesisapp.discord.client.rest.RestClient

@OptIn(ExperimentalDiscordApi::class)
class GenesisClient(private val koin: Koin) {
//    lateinit var user: ClientUser

    val rest: RestClient = RestClient(koin)

//    class ClientUser(
//        var token: String = "",
//        var email: String? = null,
//        var nsfwAllowed: Boolean = true,
//    ) : User()

//    init {
//        var packs = PremiumType.NITRO.ALLOWED_EMOJI_PACKS
//    }
}