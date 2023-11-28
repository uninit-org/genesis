package uninit.genesis.app.ui.screens.client.settings.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.getKoin
import uninit.genesis.common.preferences.PreferencesManager
import uninit.genesis.discord.client.GenesisClient
import uninit.genesis.app.ReloadClient
import uninit.genesis.app.ui.components.User.Avatar
import uninit.genesis.app.ui.components.icons.Icons
import uninit.genesis.app.ui.components.icons.icons.Empty

internal object AccountSettings : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Empty)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Account",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val koin = getKoin()
        val genesisClient = koin.get<GenesisClient>()
        val prefs = koin.get<PreferencesManager>()
        var tokenPref by prefs.preference("auth.token", "")
        val navigator = LocalNavigator.currentOrThrow
        LazyColumn {
            item {
                Row {
                    Avatar(genesisClient.normalUser)
                    Text(genesisClient.normalUser.displayName)
                }
            }
            item {
                Button(onClick = {
                    tokenPref = ""
                    ReloadClient(koin, navigator)
                }) {
                    Text("Log Out")
                }
            }
        }
    }
}