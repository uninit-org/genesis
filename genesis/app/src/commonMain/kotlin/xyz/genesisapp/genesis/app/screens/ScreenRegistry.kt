package xyz.genesisapp.genesis.app.screens

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule

sealed class SharedScreen : ScreenProvider {
    data object Landing : SharedScreen()
    data class Login(val isLogin: Boolean) : SharedScreen()
}

val ScreenModule = screenModule {
    register<SharedScreen.Landing> { LandingScreen() }
    register<SharedScreen.Login> { provider ->
        LoginScreen(
            isLogin = provider.isLogin
        )
    }
}