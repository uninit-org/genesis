package xyz.genesisapp.discord.api.domain.user

data class DomainUserProfileConnectedAccount(
    val type: String,
    val id: String,
    val name: String,
    val verified: Boolean,
//    val metadata: Metadata? = null
)
