package uninit.genesisApi.types.update

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePlugin(
    val version: String,
    val enabled: Boolean,
    val proxied: Boolean
)

@Serializable
data class UpdateRequest(
    val uuid: String,
    val version: String,
    val plugins: Map<String, UpdatePlugin>
)

@Serializable
data class UpdateResponse(
    val uuid: String,
    val updateAvailable: Boolean,
    val disabledPlugins: List<String>,
    val deletedPlugins: List<String>,
    val pluginUpdates: Map<String, String>
)