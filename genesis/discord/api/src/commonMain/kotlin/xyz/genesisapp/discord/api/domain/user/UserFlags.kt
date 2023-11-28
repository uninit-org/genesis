package uninit.genesis.discord.api.domain.user

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class UserFlags constructor(private val value: Int) {
    companion object {
        /**
         * Discord Employee
         */
        val STAFF = UserFlags(1 shl 0)

        /**
         * Partnered Server Owner
         */
        val PARTNER = UserFlags(1 shl 1)

        /**
         * HypeSquad Events
         */
        val HYPESQUAD_EVENTS = UserFlags(1 shl 2)

        /**
         * Bug Hunter Level 1
         */
        val BUG_HUNTER_LEVEL_1 = UserFlags(1 shl 3)

        /**
         * House Bravery
         */
        val HOUSE_BRAVERY = UserFlags(1 shl 6)

        /**
         * House Brilliance
         */
        val HOUSE_BRILLIANCE = UserFlags(1 shl 7)

        /**
         * House Balance
         */
        val HOUSE_BALANCE = UserFlags(1 shl 8)

        /**
         * Early Supporter
         */
        val EARLY_SUPPORTER = UserFlags(1 shl 9)

        /**
         * Team User
         */
        val TEAM_USER = UserFlags(1 shl 10)

        /**
         * Bug Hunter Level 2
         */
        val BUG_HUNTER_LEVEL_2 = UserFlags(1 shl 14)

        /**
         * Verified Bot
         */
        val VERIFIED_BOT = UserFlags(1 shl 16)

        /**
         * Early Verified Bot Developer
         */
        val EARLY_VERIFIED_BOT_DEVELOPER = UserFlags(1 shl 17)

        /**
         * Discord Certified Moderator
         */
        val DISCORD_CERTIFIED_MODERATOR = UserFlags(1 shl 18)

        /**
         * Bot uses HTTP Interactions
         */
        val DISCORD_INTERACTIONS_BOT = UserFlags(1 shl 19)

        /**
         * Active Developer
         */
        val ACTIVE_DEVELOPER = UserFlags(1 shl 22)
    }

    infix fun and(other: UserFlags): UserFlags {
        return UserFlags(this.value and other.value)
    }

    infix fun or(other: UserFlags): UserFlags {
        return UserFlags(this.value or other.value)
    }

    infix fun xor(other: UserFlags): UserFlags {
        return UserFlags(this.value xor other.value)
    }

    infix fun has(other: UserFlags): Boolean {
        return (this.value and other.value) != 0
    }

    infix fun with(other: UserFlags): UserFlags {
        return UserFlags(this.value or other.value)
    }
}