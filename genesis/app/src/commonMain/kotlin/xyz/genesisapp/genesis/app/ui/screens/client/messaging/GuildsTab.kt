package uninit.genesis.app.ui.screens.client.messaging


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import io.kamel.image.KamelImage
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import uninit.genesis.common.preferences.PreferencesManager
import uninit.genesis.discord.api.domain.user.GuildFolder
import uninit.genesis.discord.api.types.Snowflake
import uninit.genesis.discord.client.GenesisClient
import uninit.genesis.discord.entities.guild.ChannelType
import uninit.genesis.app.data.DataStore
import uninit.genesis.app.ui.components.icons.Icons
import uninit.genesis.app.ui.components.icons.icons.Empty
import kotlin.math.roundToInt

enum class GuildIconType {
    DM, GUILD, FOLDER
}

internal object GuildsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Empty)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Chat",
                    icon = icon
                )
            }
        }

    @OptIn(
        ExperimentalResourceApi::class, ExperimentalFoundationApi::class,
        ExperimentalMaterialApi::class
    )
    @Composable
    override fun Content() {
        val koin = getKoin()
        val genesisClient = koin.get<GenesisClient>()

        val prefs = koin.get<PreferencesManager>()

        var currentGuild by prefs.preference(
            "client.currentGuild",
            genesisClient.guilds.values.first()!!.id
        )
        val useDiscordIcon by prefs.preference("ui.discordIcon", false)
        var lastGuild by remember { mutableStateOf(genesisClient.guilds.values.find { it?.id != 0L }!!.id) }
        val dataStore = koin.get<DataStore>()

        val scope = rememberCoroutineScope()
        val swipeableState = rememberSwipeableState(0)

        dataStore.compositionOnToggleGuilds {
            scope.launch {
                if (swipeableState.currentValue == 0) swipeableState.animateTo(1)
                else swipeableState.animateTo(0)
            }
        }


        fun chooseGuild(id: Snowflake) {
            lastGuild = currentGuild
            currentGuild = id
            dataStore.selectGuild(id)
            val guild = genesisClient.guilds[id]!!
            dataStore.selectChannel(
                when (id) {
                    0L -> guild.channels.first().id
                    else -> guild.channels.first { it.type == ChannelType.GUILD_TEXT }.id
                }
            )
        }

        val width = 500.dp

        Box(
            modifier = Modifier.swipeable(
                state = swipeableState,
                anchors = mapOf(0f to 0, width.value to 1),
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
        ) {

            Row {
                var rerenderBool by remember { mutableStateOf(true) }
                fun rerender() {
                    rerenderBool = false
                }
                if (rerenderBool) {
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
                                modifier = if (currentGuild.toInt() == 0) {
                                    modifier.clip(RoundedCornerShape(2.dp))
                                } else {
                                    modifier.clip(CircleShape)
                                }
                            ) {
                                Image(
                                    painterResource(if (useDiscordIcon) "images/img_logo.png" else "icons/genesis.png"),
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
                                    guildIds = notHitGuilds.map { it.toString() },
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
                                            resource = guild.icon!!.render(128),
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
                } else rerenderBool = true
                Navigator(ChannelsScreen(genesisClient.guilds[currentGuild], lastGuild))
            }
            val guild = genesisClient.guilds[currentGuild]!!

            val firstChannel = when (currentGuild) {
                0L -> guild.channels.first().id
                else -> guild.channels.find { it.type == ChannelType.GUILD_TEXT }!!.id
            }

            var currentChannel by prefs.preference(
                "client.currentChannel",
                firstChannel
            )

            if (guild.channels.find { it.id == currentChannel } == null) currentChannel =
                firstChannel


            Box(
                modifier = Modifier.offset {
                    IntOffset(
                        if (dataStore.mobileUi) swipeableState.offset.value.roundToInt() else width.value.roundToInt(),
                        0
                    )
                }
            ) {
                Navigator(ChatScreen(genesisClient.channels[currentChannel], currentChannel))
            }
        }

    }
}