package xyz.genesisapp.genesis.app.ui.screens

import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.data.DataStore
import xyz.genesisapp.genesis.app.ui.screens.auth.LoginScreen
import xyz.genesisapp.genesis.app.ui.screens.client.GatewayLoadScreen
import xyz.genesisapp.genesisApi.GenesisApiClient
import xyz.genesisapp.genesisApi.types.update.UpdateRequest


class RootScreen : GenericLoadingScreen(loadingText = "Welcome to Genesis", { koin ->
    val prefs = koin.get<PreferencesManager>()
    val genesisApi = koin.get<GenesisApiClient>()
    val genesisClient = koin.get<GenesisClient>()
    var apiUUID by prefs.preference("api.uuid", "")
    val response = genesisApi.getUpdate(UpdateRequest(apiUUID, "0.0.0b", emptyMap()))
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


    val token by prefs.preference("auth.token", "")

    if (token.isNotEmpty()) {
        genesisClient.rest.token = token
        val authResponse = genesisClient.rest.getDomainMe()
        if (authResponse.isOk()) {
            val authData = authResponse.getOrNull()!!
            println("Logged in as ${authData.username}")

            GatewayLoadScreen()
        } else {
            LoginScreen()
        }
    } else {
        LoginScreen()
    }
})