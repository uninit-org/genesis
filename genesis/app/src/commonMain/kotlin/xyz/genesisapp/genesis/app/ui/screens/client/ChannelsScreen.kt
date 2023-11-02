package xyz.genesisapp.genesis.app.ui.screens.client


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.entities.guild.Channel
import xyz.genesisapp.discord.entities.guild.ChannelType
import xyz.genesisapp.genesis.app.data.DataStore

class ChannelsScreen(
) : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val genesisClient = koin.get<GenesisClient>()
        val prefs = koin.get<PreferencesManager>()
        val dataStore = koin.get<DataStore>()

        val guildIdPref by prefs.preference(
            "client.currentGuild",
            genesisClient.guilds.values.first()!!.id
        )
        var guildId by remember { mutableStateOf(guildIdPref) }

        dataStore.events.on<Snowflake>("GUILD_SELECT") {
            guildId = it
        }

        val guild = genesisClient.guilds.get(guildId) ?: throw Exception("Guild not found")
        println(guild.name)
        LazyColumn(
            modifier = Modifier
        ) {
            val categories = mutableMapOf<Snowflake, Channel>()
            guild.channels!!.forEach {
                if (it.type == ChannelType.GUILD_CATEGORY) {
                    categories[it.id] = it
                }
            }
            guild.channels!!.forEach {
                if (it.type != ChannelType.GUILD_CATEGORY) {
                    if (it.parentId != null) {
                        categories[it.parentId!!]!!.children.add(it.id)
                    }
                }
            }

            val sortedCategories = categories.values.sortedBy { it.position }
            sortedCategories.forEach { category ->
                category.children =
                    category.children.sortedBy { guild.channels!!.find { it2 -> it2.id == it }!!.position }
                        .toMutableList()
            }

            println(sortedCategories)

            sortedCategories.forEach { category ->
                if (category.children.isEmpty()) {
                    return@forEach
                }
                item {
                    Text(category.name!!)
                    category.children.forEach { channelId ->
                        val channel =
                            guild.channels!!.find { it2 -> it2.id == channelId }!!
                        Text(channel.name!!, modifier =
                        Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                dataStore.events.emit("CHANNEL_SELECT", channel.id)
                            })
                    }
                }
            }
        }

    }
}