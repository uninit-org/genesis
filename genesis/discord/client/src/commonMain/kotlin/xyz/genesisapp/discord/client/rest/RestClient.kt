package xyz.genesisapp.discord.client.rest

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.Koin
import xyz.genesisapp.common.fytix.Err
import xyz.genesisapp.common.fytix.Ok
import xyz.genesisapp.common.fytix.Result
import xyz.genesisapp.discord.api.ApiError
import xyz.genesisapp.discord.api.domain.user.DomainMe
import xyz.genesisapp.discord.api.domain.user.DomainUserProfile

class RestClient(private val koin: Koin, baseUrl: String = "https://discord.com/api/v9") {
    private val http = HttpClient(koin.get<HttpClientEngineFactory<*>>()) {
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

    var token: String
        get() = ""
        set(value) {
            http.config {
                headers {
                    set("Authentication", value)
                }
            }
        }

    internal suspend inline fun <reified T, reified E> get(endpoint: String): Result<T, E> {
        val res = http.get(endpoint)
        return when (res.status) {
            HttpStatusCode.OK -> {
                Ok(res.body())
            }
            else -> {
                Err(res.body())
            }
        }
    }

    suspend fun getDomainMe(): Result<DomainMe, ApiError>
        = get("/users/@me")
    suspend fun getDomainUser(userId: String, withMutualGuilds: Boolean = true): Result<DomainUserProfile, ApiError>
        = get("/users/$userId/profile?with_mutual_guilds=$withMutualGuilds&with_mutual_friends_count=false")
}