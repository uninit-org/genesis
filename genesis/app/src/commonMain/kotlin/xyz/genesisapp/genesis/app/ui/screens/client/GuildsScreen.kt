package xyz.genesisapp.genesis.app.ui.screens.client


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.entities.guild.Guild

class GuildsScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        val genesisClient = koin.get<GenesisClient>()

        var guilds by remember { mutableStateOf(emptyList<Guild>()) }

        var rerenderBool by remember { mutableStateOf(true) }
        fun rerender() {
            rerenderBool = false
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
        ) {
            if (rerenderBool) {
                genesisClient.userSettings!!.guildFolders.forEach { sortedFolder ->
                    if (sortedFolder.guildIds.isEmpty()) return@forEach
                    if (sortedFolder.guildIds.size == 1) {
                        sortedFolder.collapsed = false
                    } else {
                        Button(
                            onClick = {
                                sortedFolder.collapsed = !sortedFolder.collapsed
                                rerender()
                            },
                        ) {
                            Image(
                                painter = painterResource("images/folder.png"),
                                contentDescription = sortedFolder.name
                            )
                        }
                    }
                    if (!sortedFolder.collapsed) {
                        sortedFolder.guildIds.forEach forEach2@{ guildId ->
                            val guild = genesisClient.guilds[guildId]
                            if (guild === null) return@forEach2
                            Box(
                                modifier = Modifier
                                    .size(
                                        64.dp,
                                        64.dp
                                    )
                                    .background(Color.Red)
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
    }
}