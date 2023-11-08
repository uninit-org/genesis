package xyz.genesisapp.discord.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject

internal typealias ApiErrors = @Serializable(with = ApiErrorPack.ApiErrorsSerializer::class) Map<String, List<ApiErrorPack.ApiErrorManifest>>

@Serializable
data class ApiErrorPack(
    val code: Int,
    val message: String,
    val errors: ApiErrors
) {
    @Serializable
    data class ApiErrorManifest(
        val code: String,
        val message: String
    )

    internal class ApiErrorsSerializer
        : JsonTransformingSerializer<ApiErrors>(
        MapSerializer(
            String.serializer(),
            ListSerializer(ApiErrorManifest.serializer())
        )
    ) {
        override fun transformDeserialize(element: JsonElement): JsonElement {
            return buildJsonObject {
                element.jsonObject.forEach { entry: Map.Entry<String, JsonElement> ->
                    put(entry.key, entry.value.jsonObject["_errors"]!!)
                }
            }
        }
    }
}