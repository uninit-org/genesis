package xyz.genesisapp.genesis.app.ui.screens.client


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.koin.compose.getKoin
import xyz.genesisapp.genesis.app.data.DataStore
import xyz.genesisapp.genesis.app.ui.screens.client.messaging.GuildsTab
import xyz.genesisapp.genesis.app.ui.screens.client.settings.SettingsTab

enum class ClientTab(val tab: Tab) {
    GUILDS(GuildsTab),
    SETTINGS(SettingsTab)
}

class ClientRootScreen : Screen {
    @Composable
    override fun Content() {
        val koin = getKoin()
        val dataStore = koin.get<DataStore>()

        var lastTab = ClientTab.values().first().tab

        TabNavigator(lastTab) { tabNavigator ->
            dataStore.events.on<Tab>("CLIENT_TAB_SELECT") {
                lastTab = tabNavigator.current
                tabNavigator.current = it
            }
            dataStore.events.on<Boolean>("CLIENT_TAB_BACK") {
                tabNavigator.current = lastTab
            }
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = dataStore.mobileUi,
                        // slide in from bottom
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        NavigationBar {
                            ClientTab.values().map { it.tab }.forEach {
                                NavigationBarItem(
                                    selected = tabNavigator.current == it,
                                    onClick = { tabNavigator.current = it },
                                    icon = {
                                        it.options.icon?.let { it1 ->
                                            Icon(
                                                painter = it1,
                                                contentDescription = it.options.title
                                            )
                                        }
                                    },
                                    label = {
                                        it.options.title.let { it1 -> Text(it1) }
                                    }
                                )

                            }
                        }
                    }
                }
            )
        }
    }
}