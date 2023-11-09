package xyz.genesisapp.genesis.app.ui.screens.client.settings.pages

import androidx.compose.foundation.layout.Row
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
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.ReloadClient
import xyz.genesisapp.genesis.app.ui.components.User.Avatar

object AccountSettings : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Account"
        )

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