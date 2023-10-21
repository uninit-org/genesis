package xyz.genesisapp.discord.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val message: String,
    val code: Int,
)
