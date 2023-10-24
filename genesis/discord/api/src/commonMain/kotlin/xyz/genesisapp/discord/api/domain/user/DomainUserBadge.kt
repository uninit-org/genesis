package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.Serializable

@Serializable
data class DomainUserBadge (
    val id: String,
    val description: String,
    val icon: String,
    val link: String? = null
)