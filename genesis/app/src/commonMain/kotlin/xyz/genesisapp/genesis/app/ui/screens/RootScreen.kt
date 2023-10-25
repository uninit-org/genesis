package xyz.genesisapp.genesis.app.ui.screens

import kotlinx.serialization.Serializable
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.api.ApiError
import xyz.genesisapp.genesis.app.ui.screens.auth.LoginScreen

@Serializable
data class Plugin(
    val version: String,
    val enabled: Boolean,
    val proxied: Boolean
)

@Serializable
data class apiUpdateRequest(
    val uuid: String,
    val version: String,
    val plugins: Map<String, Plugin>
)

@Serializable
data class apiUpdateResponse(
    val uuid: String,
    val updateAvailable: Boolean,
    val disabledPlugins: List<String>,
    val deletedPlugins: List<String>,
    val pluginUpdates: Map<String, String>
)

class RootScreen : GenericLoadingScreen(loadingText = "Welcome to Genesis", { koin ->
    val prefs = koin.get<PreferencesManager>()
    val genesisClient = koin.get<GenesisClient>()
    var apiUUID by prefs.preference("api.uuid", "")
    val response = genesisClient.rest.post<apiUpdateRequest, apiUpdateResponse, ApiError>(
        "http://localhost:8080/api/v1/update", apiUpdateRequest(
            uuid = apiUUID,
            version = "0.0.1",
            plugins = mapOf()
        )
    )
    println(response)
    LoginScreen()
}) {

}