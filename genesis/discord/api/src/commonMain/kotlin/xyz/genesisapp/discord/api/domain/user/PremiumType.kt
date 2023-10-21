package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.Serializable
import xyz.genesisapp.discord.api.annotations.ExperimentalDiscordApi

@Serializable
@JvmInline
value class PremiumType private constructor(private val type: Int) {
    val UPLOAD_LIMIT: Int
        get() = when (type) {
            0 -> 8 * 1024 * 1024
            1 -> 8 * 1024 * 1024
            2 -> 500 * 1024 * 1024
            3 -> 50 * 1024 * 1024
            else -> throw IllegalStateException("Unknown premium type: $type")
        }
    val CAN_USE_PREMIUM_CUSTOMIZATIONS: Boolean
        get() = type == 2

    val HAS_BOOST_DISCOUNT: Boolean
        get() = type >= 2

    val IS_NITRO: Boolean
        get() = type >= 1

    @ExperimentalDiscordApi
    val ALLOWED_EMOJI_PACKS: Int
        get() = when (type) {
            2 -> 100
            else -> 1
        }

    companion object {
        val NONE = PremiumType(0)
        val NITRO_CLASSIC = PremiumType(1)
        val NITRO = PremiumType(2)
        val NITRO_BASIC = PremiumType(3)
    }
}