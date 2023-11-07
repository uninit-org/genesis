package xyz.genesisapp.genesis.app.ui.screens.client


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.genesisapp.genesis.app.ui.screens.client.messaging.GuildsScreen

class ClientRootScreen : Screen {
    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Navigator(GuildsScreen())
    }
}