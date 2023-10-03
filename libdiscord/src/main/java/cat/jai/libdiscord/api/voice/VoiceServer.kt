package cat.jai.libdiscord.api.voice

class VoiceServer(
    private val channelId: Long,
    private val endpoint: String,
    private val guildId: Long,
    private val token: String
) {

    fun getChannelId(): Long {
        return channelId
    }

    fun getEndpoint(): String {
        return endpoint
    }

    fun getGuildId(): Long {
        return guildId
    }

    fun getToken(): String {
        return token
    }

    override fun equals(other: Any?): Boolean {
        return (other is VoiceServer) && other.getChannelId() == getChannelId() &&
                other.getEndpoint() == getEndpoint() &&
                other.getGuildId() == getGuildId() &&
                other.getToken() == getToken()

    }

    override fun hashCode(): Int {
        var result = channelId.hashCode()
        result = 31 * result + endpoint.hashCode()
        result = 31 * result + guildId.hashCode()
        result = 31 * result + token.hashCode()
        return result
    }

    override fun toString(): String {
        return "VoiceServer(channelId=$channelId, endpoint='$endpoint', guildId=$guildId, token='$token')"
    }

}