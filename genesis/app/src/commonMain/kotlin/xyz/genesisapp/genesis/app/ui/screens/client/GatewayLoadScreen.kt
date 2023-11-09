package xyz.genesisapp.genesis.app.ui.screens.client


import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.ui.screens.GenericLoadingScreen

class GatewayLoadScreen : GenericLoadingScreen(loadingText = "Welcome to Genesis", { koin ->
    val prefs = koin.get<PreferencesManager>()
    val genesisClient = koin.get<GenesisClient>()

    val token by prefs.preference("auth.token", "")
    
    genesisClient.gateway.token = token
    genesisClient.gateway.connect()

    genesisClient.events.suspendOnce<String>("READY")



    ClientRootScreen()
})