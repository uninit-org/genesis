package xyz.genesisapp.genesis.app.ui.screens.client.messaging


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.core.ExperimentalKamelApi
import io.kamel.image.KamelImageBox
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.api.types.AssetType
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.api.types.toUrl
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.entities.guild.Channel
import xyz.genesisapp.discord.client.entities.guild.Guild
import xyz.genesisapp.discord.entities.guild.ChannelType
import xyz.genesisapp.genesis.app.data.DataStore
import xyz.genesisapp.genesis.app.ui.screens.EventScreen

@Composable
fun Channel(channel: Channel, select: (Channel) -> Unit) {
    var modifier = Modifier
        .clickable {
            select(channel)
        }

    if (channel.parentId != null) {
        modifier = modifier.padding(start = 16.dp)
    }

    Text(
        channel.name, modifier =
        modifier
    )

}


@Composable
fun Category(
    category: Channel?, children: List<Channel>, select: (Channel) -> Unit
) {
    if (category != null) {
        Text(category.name)
    }
    children.forEach { channel ->
        Channel(channel) {
            select(it)
        }
    }
}

class ChannelsScreen(
    private var guild: Guild
) : EventScreen() {
    @OptIn(ExperimentalResourceApi::class, ExperimentalKamelApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val genesisClient = koin.get<GenesisClient>()
        val prefs = koin.get<PreferencesManager>()
        val dataStore = koin.get<DataStore>()
        val navigator = LocalNavigator.currentOrThrow

        var currentChannel by prefs.preference(
            "client.currentChannel",
            guild.channels.find { it.type == ChannelType.GUILD_TEXT }!!.id,
        )

        Events(
            dataStore.events.quietRegister<Snowflake>("GUILD_SELECT") {
                navigator.push(ChannelsScreen(genesisClient.guilds[it]!!))
            }
        )

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            AnimatedVisibility(visible = dataStore.showGuilds || !dataStore.mobileUi) {

                LazyColumn(
                    modifier = Modifier
                        .width(
                            128.dp
                        )
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
                                currentChannel = it.id
                                dataStore.events.emit("CHANNEL_SELECT", it.id)
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
                            currentChannel = it.id
                            dataStore.events.emit("CHANNEL_SELECT", it.id)
                        }
                    }

                }
            }
            Navigator(ChatScreen(genesisClient.channels[currentChannel]!!))
        }

    }
}