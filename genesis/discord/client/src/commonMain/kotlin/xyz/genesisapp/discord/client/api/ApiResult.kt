package xyz.genesisapp.discord.client.api

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: Int,
    val message: String,
)

data class ApiResult<T>(
    val data: T? = null,
    val success: Boolean = true,
    val httpCode: HttpStatusCode,
    val error: ApiError? = null
)