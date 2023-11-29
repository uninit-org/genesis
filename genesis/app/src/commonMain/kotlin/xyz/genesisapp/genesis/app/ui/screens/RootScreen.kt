package uninit.genesis.app.ui.screens

import io.github.aakira.napier.Napier
import uninit.common.fytix.Err
import uninit.common.fytix.Ok
import uninit.common.compose.preferences.PreferencesManager
import uninit.genesis.discord.client.GenesisClient
import uninit.genesis.discord.client.enum.LogLevel
import uninit.genesis.app.ui.screens.auth.LoginScreen
import uninit.genesis.app.ui.screens.client.GatewayLoadScreen
import uninit.genesisApi.GenesisApiClient
import uninit.genesisApi.types.update.UpdateRequest


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
        when (val result = genesisClient.tryTokenLogin(token)) {
            is Ok -> {
                Napier.d(
                    "Logged in as ${result.value.username}",
                    null,
                    "Client::LOGIN"
                )
                GatewayLoadScreen()
            }

            is Err -> {
                Napier.w(
                    "Failed to login with token: ${result.error.message}",
                    null,
                    "Client::LOGIN"
                )
                LoginScreen()
            }
        }
    } else {
        Napier.d(
            "No token found",
            null,
            "Client::LOGIN"
        )
        LoginScreen()
    }
})