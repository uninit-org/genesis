package xyz.genesisapp.genesis.app.ui.screens.client.settings

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.getKoin
import xyz.genesisapp.genesis.app.data.DataStore

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Settings"
        )

    @Composable
    override fun Content() {
        val koin = getKoin()
        val dataStore = koin.get<DataStore>()
        Scaffold(
            topBar = {
                if (!dataStore.mobileUi) {
                    Button(onClick = {
                        dataStore.events.emit("CLIENT_TAB_BACK", true)
                    }) {
                        Text("Back")
                    }
                }
            }
        ) { }

    }
}