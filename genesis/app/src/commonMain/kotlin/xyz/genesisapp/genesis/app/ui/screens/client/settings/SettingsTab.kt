package xyz.genesisapp.genesis.app.ui.screens.client.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.getKoin
import xyz.genesisapp.genesis.app.data.DataStore
import xyz.genesisapp.genesis.app.ui.screens.client.settings.pages.AccountSettings

enum class SettingPage(val tab: Tab) {
    ACCOUNT(AccountSettings),
}

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Settings"
        )

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val dataStore = koin.get<DataStore>()

        var isSettingOpen by remember { mutableStateOf(false) }
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
        ) {
            TabNavigator(SettingPage.values().first().tab) { tabNavigator ->
                Box(modifier = Modifier.padding(48.dp)) {
                    Row {

                        AnimatedVisibility(visible = !isSettingOpen || !dataStore.mobileUi) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        isSettingOpen = true
                                    }
                            ) {
                                items(items = SettingPage.values().map { it.tab }) {
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                tabNavigator.current = it
                                                isSettingOpen = true
                                            }
                                    ) {
                                        it.options.icon?.let { it1 ->
                                            Icon(
                                                painter = it1,
                                                contentDescription = it.options.title
                                            )
                                        }
                                        Text(it.options.title)
                                    }
                                }
                            }
                        }
                        if (isSettingOpen || !dataStore.mobileUi) {
                            Scaffold(
                                content = {
                                    CurrentTab()
                                },
                                modifier = Modifier.background(MaterialTheme.colorScheme.onSurface),
                                topBar = {
                                    if (dataStore.mobileUi) {
                                        Button(onClick = {
                                            isSettingOpen = false
                                        }) {
                                            Text("Back")
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }

        }

    }
}