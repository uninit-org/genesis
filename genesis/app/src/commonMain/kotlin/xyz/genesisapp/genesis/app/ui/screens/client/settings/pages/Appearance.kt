package xyz.genesisapp.genesis.app.ui.screens.client.settings.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager

object AppearanceSettings : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Appearance"
        )

    @Composable
    override fun Content() {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        var discordIcon by prefs.preference("ui.discordIcon", false)
        LazyColumn {

            item {
                Row {
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