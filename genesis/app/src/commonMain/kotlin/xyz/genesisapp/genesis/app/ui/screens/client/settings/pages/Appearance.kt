package xyz.genesisapp.genesis.app.ui.screens.client.settings.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.genesis.app.ui.components.icons.Icons
import xyz.genesisapp.genesis.app.ui.components.icons.icons.Empty

internal object AppearanceSettings : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Empty)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Appearance",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        var discordIcon by prefs.preference("ui.discordIcon", false)
        LazyColumn {

            item {
                Row {
                    Text("Use Discord Icon")
                    Switch(
                        checked = discordIcon,
                        onCheckedChange = {
                            discordIcon = it
                        }
                    )
                }
            }
        }
    }
}