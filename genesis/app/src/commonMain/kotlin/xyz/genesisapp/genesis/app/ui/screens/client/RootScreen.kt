package xyz.genesisapp.genesis.app.ui.screens.client


import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.api.gateway.GatewayIntents
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.ui.screens.GenericLoadingScreen

class ClientRootScreen : GenericLoadingScreen(loadingText = "Welcome to Genesis", { koin ->
    println("root screen")
    val prefs = koin.get<PreferencesManager>()
    val genesisClient = koin.get<GenesisClient>()

    val token by prefs.preference("auth.token", "")


    genesisClient.gateway.connect(token, GatewayIntents.ALL.value)

    genesisClient.events.on<String>("READY") {
        println("ready")
    }

    var isReady = false

    genesisClient.events.suspendOnce<String>("READY")

    GuildsScreen()
})