package xyz.genesisapp.genesis.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.genesis.app.data.DataStore
import xyz.genesisapp.genesis.app.di.dataStoreModule
import xyz.genesisapp.genesis.app.di.genesisApiModule
import xyz.genesisapp.genesis.app.di.genesisClientModule
import xyz.genesisapp.genesis.app.di.httpModule
import xyz.genesisapp.genesis.app.di.preferencesModule
import xyz.genesisapp.genesis.app.theme.LocalContextColors
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.ui.screens.RootScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    Napier.base(DebugAntilog())
    val preferencesModule = preferencesModule()
    KoinApplication(application = {
        modules(
            preferencesModule,
            httpModule(),
            genesisClientModule(),
            genesisApiModule(),
            dataStoreModule()
        )
    }) {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        val genesisClient = koin.get<GenesisClient>()
        val dataStore = koin.get<DataStore>()

        val logLevel by prefs.preference("debug.logLevel", LogLevel.INFO.name)

        genesisClient.logLevel = LogLevel.fromName(logLevel)


        val themeName by remember { prefs.preference("ui.theme", "Catppuccin Mocha Rosewater") }
        ThemeProvider(
            themeName = themeName,
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize()
                    .background(LocalContextColors.currentOrThrow.background),
            ) {
                Layout(
                    measurePolicy = { measurables, constraints ->
                        if (constraints.maxHeight > constraints.maxWidth && !dataStore.mobileUi) dataStore.mobileUi =
                            true
                        if (constraints.maxHeight < constraints.maxWidth && dataStore.mobileUi) dataStore.mobileUi =
                            false
                        val placeables = measurables.map { measurable ->
                            measurable.measure(constraints)
                        }

                        layout(constraints.maxWidth, constraints.maxHeight) {
                            var yPosition = 0
                            placeables.forEach { placeable ->
                                placeable.placeRelative(x = 0, y = yPosition)
                                yPosition += placeable.height
                            }
                        }
                    },
                    content = {
                        Box(Modifier.padding(it)) {
                            Navigator(RootScreen())
                        }
                    }
                )
            }
        }
    }

}

expect fun getPlatformName(): String