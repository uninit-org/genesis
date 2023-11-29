package uninit.genesis.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import uninit.common.compose.koin.uninitModule
import uninit.common.compose.preferences.PreferencesManager
import uninit.genesis.discord.client.GenesisClient
import uninit.genesis.discord.client.enum.LogLevel
import uninit.genesis.app.data.DataStore
import uninit.genesis.app.di.dataStoreModule
import uninit.genesis.app.di.genesisApiModule
import uninit.genesis.app.di.genesisClientModule
import uninit.genesis.app.di.httpModule
import uninit.genesis.app.di.platformHttpEngineFactory
import uninit.genesis.app.di.preferencesModule
import uninit.genesis.app.theme.LocalContextColors
import uninit.genesis.app.theme.ThemeProvider
import uninit.genesis.app.ui.screens.RootScreen

@Composable
fun App() {
    Napier.base(getAntiLog())
    val preferencesModule = preferencesModule()
    KoinApplication(application = {
        modules(
            preferencesModule,
            httpModule(),
            genesisClientModule(),
            genesisApiModule(),
            dataStoreModule(),

            uninitModule(httpFactory = platformHttpEngineFactory)
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

expect fun getAntiLog(): Antilog