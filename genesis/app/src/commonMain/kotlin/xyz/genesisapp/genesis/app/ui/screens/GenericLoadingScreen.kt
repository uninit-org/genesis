package uninit.genesis.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import org.koin.core.Koin
import uninit.genesis.common.preferences.PreferencesManager
import uninit.genesis.app.data.DataStore
import uninit.genesis.app.ui.components.Centered

open class GenericLoadingScreen(
    val loadingText: String,
    val loadingProcedure: suspend (koin: Koin) -> Screen
) : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        val dataStore = koin.get<DataStore>()
        val useDiscordIcon by prefs.preference("ui.discordIcon", false)
        Centered {
            if (dataStore.shiggyEasterEgg) {
                Image(
                    painterResource("images/shiggy.gif"), // TODO: gif support :(
                    contentDescription = "Shiggy",
                    modifier = Modifier.size(100.dp)
                )
                Text("Shiggy")
            } else {
                Image(
                    painterResource(if (useDiscordIcon) "images/img_logo.png" else "icons/genesis.png"),
                    contentDescription = "${if (useDiscordIcon) "Discord" else "Genesis"} Logo",
                    modifier = Modifier.size(100.dp)
                )
                Text(loadingText)
            }
        }
        val nav = LocalNavigator.currentOrThrow

        rememberCoroutineScope().launch {
            val screen = loadingProcedure(koin)
            nav.replace(screen)
        }
    }
}