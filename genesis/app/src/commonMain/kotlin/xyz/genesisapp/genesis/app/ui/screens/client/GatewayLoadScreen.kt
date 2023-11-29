package uninit.genesis.app.ui.screens.client


import uninit.common.compose.preferences.PreferencesManager
import uninit.genesis.discord.client.GenesisClient
import uninit.genesis.app.ui.screens.GenericLoadingScreen

class GatewayLoadScreen : GenericLoadingScreen(loadingText = "Welcome to Genesis", { koin ->
    val prefs = koin.get<PreferencesManager>()
    val genesisClient = koin.get<GenesisClient>()

    val token by prefs.preference("auth.token", "")

    genesisClient.gateway.token = token
    genesisClient.gateway.connect()

    genesisClient.waitFor<Boolean>("READY")



    ClientRootScreen()
})