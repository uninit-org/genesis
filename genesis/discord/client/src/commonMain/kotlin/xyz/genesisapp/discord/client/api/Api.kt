package xyz.genesisapp.discord.client.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HeadersBuilder
import xyz.genesisapp.common.serialization.JsonParser

data class ApiOptions(
    val token: String? = null,
)

class Api(private val httpClient: HttpClient) {
    private val baseUrl = "https://discord.com/api/v9"
    private var apiOptions: ApiOptions = ApiOptions(
        token = null,
    )

    @PublishedApi
    internal fun getHttpClient(): HttpClient {
        return httpClient
    }

    @PublishedApi
    internal fun getBaseUrl(): String {
        return baseUrl
    }

    @PublishedApi
    internal fun getApiOptions(): ApiOptions {
        return apiOptions
    }

    var token: String?
        get() = apiOptions.token
        set(value) {
            apiOptions = apiOptions.copy(token = value)
        }

    suspend inline fun <reified T> get(
        path: String,
        requestedOptions: ApiOptions? = null
    ): ApiResult<T> {
        val response: HttpResponse =
            getHttpClient().get("${getBaseUrl()}/${trimPath(path)}") {
                headers {
                    initHeaders(this, requestedOptions)
                }
            }

        return parseResponse(response)
    }

    suspend inline fun <reified T, reified O> post(
        path: String,
        body: T,
        requestedOptions: ApiOptions? = null
    ): ApiResult<O> {
        val response: HttpResponse =
            getHttpClient().get("${getBaseUrl()}/${trimPath(path)}") {
                setBody(body)
                headers {
                    initHeaders(this, requestedOptions)
                }
            }

        return parseResponse(response)
    }

    @PublishedApi
    internal fun trimPath(path: String): String {
        return path.trimStart('/')
    }


    @PublishedApi
    internal fun initHeaders(headersBuilder: HeadersBuilder, requestedOptions: ApiOptions? = null) {
        val token = requestedOptions?.token ?: getApiOptions().token
        if (token != null) {
            headersBuilder.append("Authorization", token)
        }
    }

    @PublishedApi
    internal suspend inline fun <reified T> parseResponse(response: HttpResponse): ApiResult<T> {
        val responseText = response.bodyAsText()


        val jsonParser = JsonParser(responseText)

        return try {
            ApiResult(data = jsonParser.interpret<T>(), httpCode = response.status)
        } catch (e: Exception) {
            try {
                ApiResult(
                    success = false,
                    error = jsonParser.interpret<ApiError>(),
                    httpCode = response.status
                )
            } catch (e: Exception) {
                ApiResult(
                    success = false,
                    error = ApiError(
                        code = 0,
                        message = e.toString(),
                    ),
                    httpCode = response.status
                )
            }
        }
    }
}