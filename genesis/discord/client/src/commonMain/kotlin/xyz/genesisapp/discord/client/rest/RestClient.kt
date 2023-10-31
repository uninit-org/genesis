package xyz.genesisapp.discord.client.rest

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import xyz.genesisapp.common.fytix.Err
import xyz.genesisapp.common.fytix.Ok
import xyz.genesisapp.common.fytix.Result
import xyz.genesisapp.discord.api.ApiError
import xyz.genesisapp.discord.api.domain.user.DomainMe
import xyz.genesisapp.discord.api.domain.user.DomainUserProfile
import xyz.genesisapp.discord.api.domain.user.UserSettings
import xyz.genesisapp.discord.entities.guild.Guild

class RestClient(
    engineFactory: HttpClientEngineFactory<*>,
    baseUrl: String = "https://discord.com/api/v9/"
) {
    val http = HttpClient(engineFactory) {
        defaultRequest {
            url(baseUrl)
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    var token: String? = null

    internal suspend inline fun <reified T, reified E> get(endpoint: String): Result<T, E> {
        val res = http.get(endpoint) {
            headers {
                if (token !== null) header("Authorization", token)
            }
        }
        return when (res.status) {
            HttpStatusCode.OK -> {
                Ok(res.body())
            }

            else -> {
                Err(res.body())
            }
        }
    }

    suspend inline fun <reified I, reified T, reified E> post(
        endpoint: String,
        body: I
    ): Result<T, E> {
        val res = http.post(endpoint) {
            setBody(body)
        }
        return when (res.status) {
            HttpStatusCode.OK -> {
                Ok(res.body())
            }

            else -> {
                Err(res.body())
            }
        }
    }

    suspend fun getDomainMe(): Result<DomainMe, ApiError> = get("users/@me")
    suspend fun getDomainUser(
        userId: String,
        withMutualGuilds: Boolean = true
    ): Result<DomainUserProfile, ApiError> =
        get("users/$userId/profile?with_mutual_guilds=$withMutualGuilds&with_mutual_friends_count=false")

    suspend fun getGuild(guildId: String): Result<Guild, ApiError> = get("guilds/$guildId")
    suspend fun getUserSettings(): Result<UserSettings, ApiError> = get("users/@me/settings")
}