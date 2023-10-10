package xyz.genesisapp.genesis.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.theme.builtin.AllThemes
import xyz.genesisapp.genesis.app.views.LoginScreen

class GlobalState {
    var currentTheme by mutableStateOf(AllThemes[0])
    var currentFont by mutableStateOf(FontFamily.Default)
}

val LocalAppState = compositionLocalOf { GlobalState() }


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    CompositionLocalProvider(LocalAppState provides GlobalState()) {
        ThemeProvider(LocalAppState.current.currentTheme) {
            Scaffold(
                modifier = androidx.compose.ui.Modifier
                    .background(LocalAppState.current.currentTheme.getColors().background)
                    .fillMaxSize()
            ) { pv ->

                LoginScreen(LocalAppState.current, pv)
            }
        }
    }
}

expect fun getPlatformName(): String