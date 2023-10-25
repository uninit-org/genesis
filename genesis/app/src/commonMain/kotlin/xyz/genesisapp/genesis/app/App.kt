package xyz.genesisapp.genesis.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.genesis.app.di.genesisApiModule
import xyz.genesisapp.genesis.app.di.genesisClientModule
import xyz.genesisapp.genesis.app.di.httpModule
import xyz.genesisapp.genesis.app.di.preferencesModule
import xyz.genesisapp.genesis.app.theme.LocalContextColors
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.theme.builtin.AllThemes
import xyz.genesisapp.genesis.app.ui.screens.RootScreen

class GlobalState {
    var currentTheme by mutableStateOf(AllThemes[0])
    var currentFont by mutableStateOf(FontFamily.Default)
}

val LocalAppState = compositionLocalOf { GlobalState() }

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val preferencesModule = preferencesModule()
    KoinApplication(application = {
        modules(preferencesModule, httpModule(), genesisClientModule(), genesisApiModule())
    }) {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()


        val themeName by remember { prefs.preference("ui.theme", "Catppuccin Mocha Rosewater") }
        ThemeProvider(
            themeName = themeName,
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize()
                    .background(LocalContextColors.currentOrThrow.background),
            ) {
                Box(Modifier.padding(it)) {
                    Navigator(RootScreen())
                }
            }
        }
    }

}

expect fun getPlatformName(): String