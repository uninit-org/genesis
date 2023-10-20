package xyz.genesisapp.genesis.app.ui.screens.Auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

sealed class AuthSharedScreen : ScreenProvider {
    data object Landing : AuthSharedScreen()
    data class Login(val isLogin: Boolean) : AuthSharedScreen()
    data object TokenLogin : AuthSharedScreen()
}

val AuthScreenModule = screenModule {
    register<AuthSharedScreen.Landing> { LandingScreen() }
    register<AuthSharedScreen.Login> { provider ->
        LoginScreen(
            isLogin = provider.isLogin
        )
    }
    register<AuthSharedScreen.TokenLogin> { TokenLoginScreen() }
}

class AuthScreenView : Screen {

    @Composable
    override fun Content() {
        ScreenRegistry {
            AuthScreenModule()
        }
        Navigator(LandingScreen())
    }
}