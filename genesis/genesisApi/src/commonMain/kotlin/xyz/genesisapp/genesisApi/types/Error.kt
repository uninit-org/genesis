package xyz.genesisapp.genesisApi.types

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val message: String
)
