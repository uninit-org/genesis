package xyz.genesisapp.discord.api.domain

import kotlinx.serialization.json.Json
import xyz.genesisapp.discord.api.ApiErrorPack
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiErrorPackTests {
    val json = Json {
        ignoreUnknownKeys = true
    }

    @Test
    fun `ApiError should deserialize correctly (1)`() {
        val text = """
            {
                "message": "Invalid Form Body",
                "code": 50035,
                "errors": {
                    "login": {
                        "_errors": [
                            {
                                "code": "ACCOUNT_LOGIN_VERIFICATION_EMAIL",
                                "message": "New login location detected, please check your e-mail."
                            }
                        ]
                    }
                }
            }
        """.trimIndent()

        val result = json.decodeFromString<ApiErrorPack>(text)

        assertEquals(
            result,
            ApiErrorPack(
                message = "Invalid Form Body",
                code = 50035,
                errors = mapOf(
                    "login" to listOf(
                        ApiErrorPack.ApiErrorManifest(
                            code = "ACCOUNT_LOGIN_VERIFICATION_EMAIL",
                            message = "New login location detected, please check your e-mail."
                        )
                    )
                )
            ),
            "ApiError should deserialize correctly (1)"
        )

    }
}