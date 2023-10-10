package xyz.genesisapp.discord

import xyz.genesisapp.discord.types.Getter
import xyz.genesisapp.discord.types.getter
import xyz.genesisapp.discord.api.user.PremiumType
import xyz.genesisapp.discord.api.user.User
import xyz.genesisapp.discord.api.user.UserFlags

class GenesisClient {
    val user: ClientUser = ClientUser()
    class ClientUser internal constructor(): User() {

    }

    init {
        var packs = PremiumType.NITRO.ALLOWED_EMOJI_PACKS
    }
}