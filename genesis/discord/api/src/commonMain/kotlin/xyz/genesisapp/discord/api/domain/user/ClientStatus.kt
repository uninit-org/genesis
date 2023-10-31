package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

enum class Status {
    dnd,
    idle,
    invisible,
    offline,
    online,
}

@Serializable
data class ClientStatus(
    @SerialName("desktop")
    private val _desktop: String? = null,
    @SerialName("mobile")
    private val _mobile: String? = null,
    @SerialName("web")
    private val _web: String? = null,
) {
    @Transient
    val desktop: Status = _desktop?.let { Status.valueOf(it) } ?: Status.offline

    @Transient
    val mobile: Status = _mobile?.let { Status.valueOf(it) } ?: Status.offline

    @Transient
    val web: Status = _web?.let { Status.valueOf(it) } ?: Status.offline
}
