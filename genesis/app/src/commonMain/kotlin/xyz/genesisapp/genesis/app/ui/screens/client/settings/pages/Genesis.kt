package xyz.genesisapp.genesis.app.ui.screens.client.settings.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.genesis.app.ReloadClient
import xyz.genesisapp.genesis.app.ui.screens.client.settings.SettingPage

object GenesisSettings : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Genesis"
        )

    @Composable
    override fun Content() {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        val navigator = LocalNavigator.currentOrThrow
        LazyColumn {

            item {
                Button(onClick = {
                    ReloadClient(koin, navigator)
                }) {
                    Text("Reload")
                }
            }
        }

    }
}