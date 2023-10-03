package cat.jai.libdiscord.api

class UtcDateTime(private val millis: Long) : Comparable<UtcDateTime> {
    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: UtcDateTime): Int {
        return millis.compareTo(other.millis)
    }

    val asMillis: Long
        get() = millis
}