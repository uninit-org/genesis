package xyz.genesisapp.genesis.app.ui.screens

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule
import xyz.genesisapp.genesis.app.ui.screens.Auth.AuthScreenView

sealed class RootSharedScreen : ScreenProvider {
    data object Loading : RootSharedScreen()
    data object Auth : RootSharedScreen()
}

val RootScreenModule = screenModule {
    register<RootSharedScreen.Loading> { LoadingScreen() }
    register<RootSharedScreen.Auth> { AuthScreenView() }
}