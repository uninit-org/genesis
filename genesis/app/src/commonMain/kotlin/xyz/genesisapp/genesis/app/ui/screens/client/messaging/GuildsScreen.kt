package xyz.genesisapp.genesis.app.ui.screens.client.messaging


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.api.types.AssetType
import xyz.genesisapp.discord.api.types.toUrl
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.data.DataStore

class GuildsScreen : Screen {
    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        println("guild")
        val koin = getKoin()
        val genesisClient = koin.get<GenesisClient>()

        val prefs = koin.get<PreferencesManager>()

        var currentGuild by prefs.preference(
            "client.currentGuild",
            genesisClient.guilds.values.first()!!.id
        )
        val dataStore = koin.get<DataStore>()

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            var rerenderBool by remember { mutableStateOf(true) }
            fun rerender() {
                rerenderBool = false
            }
            if (rerenderBool) {
                AnimatedVisibility(visible = dataStore.showGuilds || !dataStore.mobileUi) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(64.dp)
                    ) {
                        items(
                            items = genesisClient.userSettings!!.guildFolders,
                            key = { it.id ?: it.guildIds.first() }
                        ) { sortedFolder ->
                            if (sortedFolder.guildIds.isEmpty()) return@items
                            if (sortedFolder.guildIds.size == 1) {
                                sortedFolder.collapsed = false
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(
                                            48.dp,
                                            48.dp
                                        )
                                        .clickable {
                                            sortedFolder.collapsed = !sortedFolder.collapsed
                                            rerender()
                                        }
                                ) {
                                    Image(
                                        painter = painterResource("images/folder.png"),
                                        contentDescription = sortedFolder.name,
                                        colorFilter = ColorFilter.tint(
                                            Color(
                                                sortedFolder.color ?: 0x5865f2
                                            ).copy(alpha = 0.5f)
                                        ),
                                        modifier = Modifier
                                            .size(24.dp)
                                            .align(Alignment.Center)
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
                                            .clickable {
                                                currentGuild = guildId.toLong()
                                                dataStore.events.emit(
                                                    "GUILD_SELECT",
                                                    guildId.toLong()
                                                )
                                            }
                                    ) {
                                        if (guild.icon !== null) KamelImage(
                                            resource = asyncPainterResource(
                                                guild.icon!!.toUrl(
                                                    AssetType.Icon, guild.id,
                                                    128
                                                )
                                            ),
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
                    }
                }
            } else rerenderBool = true

            Navigator(ChannelsScreen(genesisClient.guilds[currentGuild]!!))
        }
    }
}