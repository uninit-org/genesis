package cat.jai.libdiscord.api.voice.stage

enum class RequestToSpeakState(
    val isRequestingToSpeak: Boolean,
    val canBeInvitedToSpeak: Boolean
) {
    NONE(false, true),
    REQUESTED_TO_SPEAK(true, true),
    REQUESTED_TO_SPEAK_AND_AWAITING_USER_ACK(true, false),
    ON_STAGE(false, false)
}