package xyz.genesisapp.genesis.app.ui.screens

import io.github.aakira.napier.Napier
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.genesis.app.ui.screens.auth.LoginScreen
import xyz.genesisapp.genesis.app.ui.screens.client.GatewayLoadScreen
import xyz.genesisapp.genesisApi.GenesisApiClient
import xyz.genesisapp.genesisApi.types.update.UpdateRequest


class RootScreen : GenericLoadingScreen(loadingText = "Welcome to Genesis", { koin ->
    val prefs = koin.get<PreferencesManager>()
    val genesisApi = koin.get<GenesisApiClient>()
    val genesisClient = koin.get<GenesisClient>()
    var apiUUID by prefs.preference("api.uuid", "")
    try {
        val response = genesisApi.getUpdate(UpdateRequest(apiUUID, "0.0.0b", emptyMap()))
        val data = response.getOrNull()
        if (data != null) {
            apiUUID = data.uuid
            if (data.updateAvailable) {
                if (genesisClient.logLevel >= LogLevel.INFO) Napier.i(
                    "Update available",
                    null,
                    "Genesis Api"
                )
            }
            if (data.pluginUpdates.isNotEmpty()) {
                if (genesisClient.logLevel >= LogLevel.INFO) Napier.i(
                    "${data.pluginUpdates.size} plugin updates available",
                    null,
                    "Genesis Api"
                )
            }
        }
    } catch (e: Exception) {
        if (genesisClient.logLevel >= LogLevel.ERROR) Napier.e(
            "Error checking for updates",
            e,
            "Genesis Api"
        )
    }


    val token by prefs.preference("auth.token", "")

    if (token.isNotEmpty()) {
        genesisClient.rest.token = token
        val authResponse = genesisClient.rest.getDomainMe()
        if (authResponse.isOk()) {
            val authData = authResponse.getOrNull()!!
            if (genesisClient.logLevel >= LogLevel.INFO) Napier.i(
                "Logged in as ${authData.username}",
                null,
                "Initialization"
            )

            GatewayLoadScreen()
        } else {
            LoginScreen()
        }
    } else {
        LoginScreen()
    }
})