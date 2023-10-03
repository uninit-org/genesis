package cat.jai.libdiscord.api.voice

import cat.jai.libdiscord.api.UtcDateTime

class VoiceState(
    private val channelId: Long,
    private val deaf: Boolean,
    private val guildId: Long,
    private val member: Any, //TODO: Implement GuildMember class
    private val mute: Boolean,
    private val requestToSpeakTimestamp: UtcDateTime,
    private val selfDeaf: Boolean,
    private val selfMute: Boolean,
    private val selfStream: Boolean,
    private val selfVideo: Boolean,
    private val sessionId: String,
    private val suppress: Boolean,
    private val userId: Long
) {

}