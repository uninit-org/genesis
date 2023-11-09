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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import xyz.genesisapp.discord.api.domain.user.GuildFolder
import xyz.genesisapp.discord.api.types.AssetType
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.api.types.toUrl
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.entities.guild.ChannelType
import xyz.genesisapp.genesis.app.data.DataStore

enum class GuildIconType {
    DM, GUILD, FOLDER
}

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
        var lastGuild by remember { mutableStateOf(genesisClient.guilds.values.find { it?.id != 0L }!!.id) }
        val dataStore = koin.get<DataStore>()

        fun chooseGuild(id: Snowflake) {
            lastGuild = currentGuild
            currentGuild = id
            dataStore.events.emit(
                "GUILD_SELECT",
                id
            )
            val guild = genesisClient.guilds[id]!!
            dataStore.events.emit(
                "CHANNEL_SELECT",
                when(id) {
                    0L -> guild.channels.first().id
                    else -> guild.channels.first { it.type == ChannelType.GUILD_TEXT }.id
                }
            )
        }

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
                        item(key = "DMS") {
                            val modifier = Modifier
                                .size(
                                    48.dp,
                                    48.dp
                                )
                                .clickable {
                                    chooseGuild(0L)
                                }

                            Box(
                                modifier = if (currentGuild.toInt() ==0) {
                                    modifier.clip(RoundedCornerShape(2.dp))
                                } else {
                                    modifier.clip(CircleShape)
                                }
                            ) {
                                Image(
                                    painter = painterResource("icons/genesis.png"),
                                    contentDescription = "DMs",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }

                        val notHitGuilds = genesisClient.guilds.keys.toMutableList()
                        notHitGuilds.remove(0L)
                        genesisClient.userSettings!!.guildFolders.forEach { folder ->
                            folder.guildIds.forEach { guildId ->
                                notHitGuilds.remove(guildId.toLong())
                            }
                        }

                        if (notHitGuilds.size > 0) {
                            genesisClient.userSettings!!.guildFolders.add(
                                GuildFolder(
                                    id = null,
                                    name = "Other",
                                    color = null,
                                    guildIds = notHitGuilds.map { it.toString()  },
                                    collapsed = false
                                )
                            )
                        }



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
                                    val modifier = Modifier
                                        .size(
                                            48.dp,
                                            48.dp
                                        )
                                        .clickable {
                                            chooseGuild(guildId.toLong())
                                        }

                                    Box(
                                        modifier = if (currentGuild == guildId.toLong()) {
                                            modifier.clip(RoundedCornerShape(2.dp))
                                        } else {
                                            modifier.clip(CircleShape)
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

            Navigator(ChannelsScreen(genesisClient.guilds[currentGuild], lastGuild))
        }
    }
}