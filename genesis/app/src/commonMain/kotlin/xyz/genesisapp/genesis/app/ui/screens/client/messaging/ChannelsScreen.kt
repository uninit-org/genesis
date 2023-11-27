package xyz.genesisapp.genesis.app.ui.screens.client.messaging


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.aakira.napier.Napier
import io.kamel.core.ExperimentalKamelApi
import io.kamel.image.KamelImage
import io.kamel.image.KamelImageBox
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.api.types.AssetType
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.api.types.timestamp
import xyz.genesisapp.discord.api.types.toUrl
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.entities.guild.Channel
import xyz.genesisapp.discord.client.entities.guild.Guild
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.discord.entities.guild.ChannelType
import xyz.genesisapp.genesis.app.data.DataStore
import xyz.genesisapp.genesis.app.ui.components.User.Avatar
import xyz.genesisapp.genesis.app.ui.components.icons.Icons
import xyz.genesisapp.genesis.app.ui.components.icons.icons.Textchannel
import xyz.genesisapp.genesis.app.ui.screens.client.ClientTab

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Channel(channel: Channel, select: (Channel) -> Unit) {
    var modifier = Modifier
        .clickable {
            select(channel)
        }

    val icon = when (channel.type) {
        ChannelType.GUILD_TEXT -> Icons.Textchannel
        else -> null
    }

    Row {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = modifier.padding(start = 8.dp, end = 4.dp)
            )
        } else if (channel.type == ChannelType.DM) {
            Avatar(channel.recipients.first())
        } else if (channel.type == ChannelType.GROUP_DM) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .clip(shape = CircleShape)
            ) {
                if (channel.icon != null) {
                    KamelImage(
                        resource = asyncPainterResource(
                            channel.icon!!.toUrl(
                                AssetType.Avatar,
                                channel.id,
                                128
                            )
                        ),
                        contentDescription = channel.name,
                    )
                } else {
                    Image(
                        painter = painterResource("images/defaults/group_dm_icon_${channel.id.timestamp % 8}.png"),
                        contentDescription = channel.name,
                    )
                }
            }
        } else modifier = modifier.padding(start = 12.dp)
        Text(
            channel.name, modifier =
            modifier
        )
    }
}


@Composable
fun Category(
    category: Channel?, children: List<Channel>, select: (Channel) -> Unit
) {
    if (category != null) {
        Text(category.name,
            modifier = Modifier.clickable {
                category.isCollapsed.value = !category.isCollapsed.value
            })
    }
    if (category?.isCollapsed?.value != true) {
        children.forEach { channel ->
            Channel(channel) {
                select(it)
            }
        }
    }
}

class ChannelsScreen(
    private var _guild: Guild?,
    private val lastGuild: Snowflake
) : Screen {
    @OptIn(ExperimentalResourceApi::class, ExperimentalKamelApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val genesisClient = koin.get<GenesisClient>()
        val prefs = koin.get<PreferencesManager>()
        val dataStore = koin.get<DataStore>()
        val navigator = LocalNavigator.currentOrThrow

        if (_guild == null) {
            if (genesisClient.logLevel >= LogLevel.ERROR) Napier.e(
                "Invalid guild",
                null,
                "Channels Screen"
            )
            navigator.replace(ChannelsScreen(genesisClient.guilds[lastGuild]!!, lastGuild))
            return
        }

        val guild = _guild!!

        val firstChannel = when (guild.id) {
            0L -> guild.channels.first().id
            else -> guild.channels.find { it.type == ChannelType.GUILD_TEXT }!!.id
        }

        var currentChannel by prefs.preference(
            "client.currentChannel",
            firstChannel
        )

        if (guild.channels.find { it.id == currentChannel } == null) currentChannel = firstChannel


        var lastChannel by remember {
            mutableStateOf(currentChannel)
        }

        Events(
            dataStore.events.quietRegister<Snowflake>("GUILD_SELECT") {
                navigator.push(ChannelsScreen(genesisClient.guilds[it], guild.id))
            }
        )

        Scaffold(
            modifier = Modifier
                .width(
                    200.dp
                ),
            bottomBar = {
                AnimatedVisibility(
                    visible = !dataStore.mobileUi,
                    // slide in from bottom
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(color = MaterialTheme.colorScheme.primary)
                    ) {
                        val modifier = Modifier.align(Alignment.CenterVertically)
                        Avatar(genesisClient.normalUser, modifier = modifier)
                        Text(
                            genesisClient.normalUser.displayName,
                            modifier = modifier.width(100.dp),
                            fontSize = MaterialTheme.typography.labelMedium.fontSize
                        )
                        Button(
                            onClick = {
                                dataStore.selectClientTab(ClientTab.SETTINGS)

                            }
                        ) {
                            Text("Settings")
                        }
                    }
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 48.dp)
            ) {

                val uncategorized = mutableMapOf<Snowflake, Channel>()
                val categories = mutableMapOf<Snowflake, Channel>()

                guild.channels.forEach {
                    if (it.type == ChannelType.GUILD_CATEGORY) {
                        categories[it.id] = it
                    }
                }
                guild.channels.forEach {
                    if (it.type != ChannelType.GUILD_CATEGORY) {
                        if (it.parentId != null) {
                            if (categories[it.parentId!!]?.children?.contains(it.id) == true) {
                                return@forEach
                            }
                            categories[it.parentId!!]!!.children.add(it.id)
                        } else {
                            uncategorized[it.id] = it
                        }
                    }
                }

                val sortedCategories = categories.values.sortedBy { it.position }
                val sortedUncategorized = uncategorized.values.sortedBy { it.position }
                val iterator = sortedCategories.iterator()
                while (iterator.hasNext()) {
                    val category = iterator.next()
                    category.children =
                        category.children.sortedBy { guild.channels.find { it2 -> it2.id == it }!!.position }
                            .toMutableList()
                }


                item {
                    val modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                    if (guild.banner != null) {
                        KamelImageBox(
                            resource = asyncPainterResource(
                                guild.banner!!.toUrl(AssetType.Banner, guild.id, 480),
                            ),
                            modifier = modifier,
                            onFailure = {
                                Text(guild.name)
                            }
                        )
                        {
                            Text(guild.name)
                        }
                    }
                }



                if (sortedUncategorized.isNotEmpty())
                    item {
                        Category(null, sortedUncategorized) {
                            lastChannel = currentChannel
                            currentChannel = it.id
                            dataStore.selectChannel(it.id)
                        }
                    }

                items(
                    items = sortedCategories,
                    key = { it.id }
                ) { category ->
                    val children = category.children.map { channelId ->
                        guild.channels.find { it2 -> it2.id == channelId }!!
                    }
                    Category(category, children) {
                        lastChannel = currentChannel
                        currentChannel = it.id
                        dataStore.selectChannel(it.id)
                    }
                }

            }
        }
    }
}