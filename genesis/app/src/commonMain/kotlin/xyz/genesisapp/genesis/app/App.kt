package xyz.genesisapp.genesis.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.theme.builtin.AllThemes
import xyz.genesisapp.genesis.app.theme.builtin.Catppuccin
import xyz.genesisapp.genesis.app.views.LoginScreen

class GlobalState {
    var currentTheme by mutableStateOf(AllThemes[0])
    var currentFont by mutableStateOf(FontFamily.Default)
}

val LocalAppState = compositionLocalOf { GlobalState() }


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    ThemeProvider(Catppuccin.Latte.Rosewater) {
        Scaffold(
            modifier = Modifier
//                .background(background)
                .fillMaxSize()
        ) { pv ->
            Box(
                modifier = Modifier.padding(pv)
            ) {
                Text("Hello world!")
            }

        }
    }
}

expect fun getPlatformName(): String