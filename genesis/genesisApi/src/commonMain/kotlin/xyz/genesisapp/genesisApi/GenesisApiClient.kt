package xyz.genesisapp.genesisApi

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import xyz.genesisapp.common.fytix.Err
import xyz.genesisapp.common.fytix.Ok
import xyz.genesisapp.common.fytix.Result
import xyz.genesisapp.genesisApi.types.Error
import xyz.genesisapp.genesisApi.types.update.UpdateRequest
import xyz.genesisapp.genesisApi.types.update.UpdateResponse

class GenesisApiClient(
    engineFactory: HttpClientEngineFactory<*>,
    baseUrl: String = "https://genesisapi.loveh.art/api/v1/"
) {
    val http = HttpClient(engineFactory) {
        defaultRequest {
            url(baseUrl)
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
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

    suspend fun getUpdate(body: UpdateRequest): Result<UpdateResponse, Error> = post(
        "update",
        body
    )
}