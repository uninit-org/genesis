package xyz.genesisapp.genesis.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.genesis.app.di.initKoin
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.theme.builtin.AllThemes
import xyz.genesisapp.genesis.app.theme.builtin.Catppuccin

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
    val module = initKoin()
    KoinApplication(application = {
        modules(module)
    }) {
        val koin = getKoin()
//        var theme by remember { koin.get<MutableState<Theme>>() }
        ThemeProvider(
            Catppuccin.Mocha.Rosewater
        ) {
            Scaffold {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    val prefs = koin.get<PreferencesManager>()
                    var count by remember { prefs.preference("test.count", 0) }
                    var count2 by remember { mutableStateOf(0) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Button(
                            onClick = {
                                count++
                            },
                        ) {
                            Text("Count: $count")
                        }

                        Button(
                            onClick = {
                                count2++
                            },
                        ) {
                            Text("Count2: $count2")
                        }

                    }
                }
            }
        }
    }

}

expect fun getPlatformName(): String