package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.Serializable

@Serializable
data class DomainUserProfileMutualGuild(
    val id: String,
    val nick: String? = null
)
