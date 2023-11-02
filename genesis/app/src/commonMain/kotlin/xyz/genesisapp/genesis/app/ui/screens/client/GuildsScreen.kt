package xyz.genesisapp.genesis.app.ui.screens.client


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.data.DataStore

class GuildsScreen : Screen {
    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val genesisClient = koin.get<GenesisClient>()

        val prefs = koin.get<PreferencesManager>()

        var currentGuild by prefs.preference(
            "client.currentGuild",
            genesisClient.guilds.values.first()!!.id
        )
        val dataStore = koin.get<DataStore>()

        var rerenderBool by remember { mutableStateOf(true) }
        fun rerender() {
            rerenderBool = false
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxHeight()
                        .width(64.dp)
                ) {
                    if (rerenderBool) {
                        genesisClient.userSettings!!.guildFolders.forEach { sortedFolder ->
                            if (sortedFolder.guildIds.isEmpty()) return@forEach
                            if (sortedFolder.guildIds.size == 1) {
                                sortedFolder.collapsed = false
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(
                                            48.dp,
                                            48.dp
                                        )
                                        .onClick {
                                            sortedFolder.collapsed = !sortedFolder.collapsed
                                            rerender()
                                        }
                                ) {
                                    Image(
                                        painter = painterResource("images/folder.png"),
                                        contentDescription = sortedFolder.name
                                    )
                                }

                            }
                            if (!sortedFolder.collapsed) {
                                sortedFolder.guildIds.forEach forEach2@{ guildId ->
                                    val guild = genesisClient.guilds[guildId.toLong()]
                                    if (guild === null) return@forEach2
                                    Box(
                                        modifier = Modifier
                                            .size(
                                                48.dp,
                                                48.dp
                                            )
                                            .onClick {
                                                currentGuild = guildId.toLong()
                                                dataStore.events.emit(
                                                    "GUILD_SELECT",
                                                    guildId.toLong()
                                                )
                                            }
                                    ) {
                                        if (guild.icon !== null) KamelImage(
                                            resource = asyncPainterResource("https://cdn.discordapp.com/icons/${guild.id}/${guild.icon}.png"),
                                            contentDescription = guild.name
                                        ) else {
                                            var str = ""
                                            guild.name.split(" ").forEach { word ->
                                                str += word[0]
                                            }

                                            Text(str)
                                        }
                                    }
                                }
                            }
                        }
                    } else rerenderBool = true
                }

                Navigator(ChannelsScreen())
            }
        }
    }
}