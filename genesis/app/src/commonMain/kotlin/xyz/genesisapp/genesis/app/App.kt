package xyz.genesisapp.genesis.app

import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.genesisapp.genesis.app.screens.LandingScreen
import xyz.genesisapp.genesis.app.screens.ScreenModule
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.theme.builtin.AllThemes

class GlobalState {
    var currentTheme by mutableStateOf(AllThemes[0])
    var currentFont by mutableStateOf(FontFamily.Default)
}

val LocalAppState = compositionLocalOf { GlobalState() }

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    ScreenRegistry {
        ScreenModule()
    }

    ThemeProvider {
        Navigator(LandingScreen())
    }


}

expect fun getPlatformName(): String