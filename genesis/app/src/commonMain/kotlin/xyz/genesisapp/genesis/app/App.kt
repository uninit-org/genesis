package xyz.genesisapp.genesis.app

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.dsl.module
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.di.preferencesModule
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.theme.builtin.AllThemes
import xyz.genesisapp.genesis.app.ui.screens.LoadingScreen
import xyz.genesisapp.genesis.app.ui.screens.RootScreenModule

class GlobalState {
    var currentTheme by mutableStateOf(AllThemes[0])
    var currentFont by mutableStateOf(FontFamily.Default)
}

val LocalAppState = compositionLocalOf { GlobalState() }

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {

//    ScreenRegistry {
//        ScreenModule()
//    }
//
//    ThemeProvider {
//        Navigator(LandingScreen())
//    }
    val preferencesModule = preferencesModule()
    val genesisClientModule = module {
        single { GenesisClient() }
    }
    KoinApplication(application = {
        modules(preferencesModule, genesisClientModule)
    }) {
        val prefs = getKoin().get<PreferencesManager>()

        val themeName by remember { prefs.preference("ui.theme", "Catppuccin Mocha Rosewater") }
        ThemeProvider(
            themeName = themeName,
        ) {
            ScreenRegistry {
                RootScreenModule()
            }
            Navigator(LoadingScreen())
        }
    }

}

expect fun getPlatformName(): String