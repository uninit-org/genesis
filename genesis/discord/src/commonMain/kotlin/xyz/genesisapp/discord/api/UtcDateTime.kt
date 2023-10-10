package xyz.genesisapp.discord.api

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@Serializable
@JvmInline
value class UtcDateTime(@PublishedApi internal val millis: Long) : Comparable<UtcDateTime> {

    companion object {
        @JvmStatic
        val DISCORD_EPOCH = 1420070400000L
    }

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: UtcDateTime): Int {
        return millis.compareTo(other.millis)
    }

    inline val asUnixMillis: Long
        get() = millis + DISCORD_EPOCH
}