package xyz.genesisapp.genesis.app.ui.screens

import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.api.ApiError
import xyz.genesisapp.genesis.app.ui.screens.auth.LoginScreen
import xyz.genesisapp.genesisApi.GenesisApiClient
import xyz.genesisapp.genesisApi.types.update.UpdateRequest
import xyz.genesisapp.genesisApi.types.update.UpdateResponse


class RootScreen : GenericLoadingScreen(loadingText = "Welcome to Genesis", { koin ->
    val prefs = koin.get<PreferencesManager>()
    val genesisApi = koin.get<GenesisApiClient>()
    var apiUUID by prefs.preference("api.uuid", "")
    val response = genesisApi.post<UpdateRequest, UpdateResponse, ApiError>(
        "update", UpdateRequest(
            uuid = apiUUID,
            version = "0.0.1",
            plugins = mapOf()
        )
    )
    val data = response.getOrNull()
    if (data != null) {
        apiUUID = data.uuid
        if (data.updateAvailable) {
            println("Update available")
        }
        if (data.pluginUpdates.isNotEmpty()) {
            println("${data.pluginUpdates.size} plugin updates available")
        }
    }
    LoginScreen()
}) {

}