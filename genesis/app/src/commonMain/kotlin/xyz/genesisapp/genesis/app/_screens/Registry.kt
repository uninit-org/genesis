package xyz.genesisapp.genesis.app.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import xyz.genesisapp.genesis.app.screens.Auth.AuthScreenView

sealed class RootSharedScreen : ScreenProvider {
    data object Loading : RootSharedScreen()
    data object Auth : RootSharedScreen()
}

val RootScreenModule = screenModule {
    register<RootSharedScreen.Loading> { LoadingScreen() }
    register<RootSharedScreen.Auth> { AuthScreenView() }
}

class RootScreenView : Screen {

    @Composable
    override fun Content() {
        ScreenRegistry {
            RootScreenModule()
        }
        Navigator(LoadingScreen())
    }
}