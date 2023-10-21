package xyz.genesisapp.discord.client

import io.ktor.client.HttpClient
import xyz.genesisapp.discord.api.annotations.ExperimentalDiscordApi
import xyz.genesisapp.discord.api.domain.user.PremiumType
import xyz.genesisapp.discord.client.api.Api
import xyz.genesisapp.discord.client.entities.user.User

@OptIn(ExperimentalDiscordApi::class)
class GenesisClient {
    var user: ClientUser = ClientUser()
    val httpClient: HttpClient = HttpClient()

    val api: Api = Api(httpClient)

    class ClientUser(
        var token: String = "",
        var email: String? = null,
        var nsfwAllowed: Boolean = true,
    ) : User()

    init {
        var packs = PremiumType.NITRO.ALLOWED_EMOJI_PACKS
    }
}