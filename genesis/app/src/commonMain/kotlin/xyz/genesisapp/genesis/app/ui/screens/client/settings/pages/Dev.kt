package uninit.genesis.app.ui.screens.client.settings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.getKoin
import uninit.genesis.common.preferences.PreferencesManager
import uninit.genesis.discord.client.GenesisClient
import uninit.genesis.discord.client.enum.LogLevel
import uninit.genesis.app.ui.components.icons.Icons
import uninit.genesis.app.ui.components.icons.icons.Empty

internal object DevSettings : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Empty)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Developer",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        val genesisClient = koin.get<GenesisClient>()
        var logLevel by prefs.preference("debug.logLevel", LogLevel.INFO.name)
        LazyColumn {

            item {
                Row {
                    var logLevelShown by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier.clickable {
                            logLevelShown = !logLevelShown
                        }
                            .width(100.dp)
                            .height(50.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Text(logLevel)
                    }

                    DropdownMenu(
                        expanded = logLevelShown,
                        onDismissRequest = { /*TODO*/ },
                        modifier = Modifier.width(100.dp)
                    ) {
                        LogLevel.values().forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level.name) },
                                onClick = {
                                    logLevelShown = false
                                    logLevel = level.name
                                    genesisClient.logLevel = level
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}