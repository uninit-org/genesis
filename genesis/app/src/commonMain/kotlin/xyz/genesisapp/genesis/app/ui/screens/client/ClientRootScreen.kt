package xyz.genesisapp.genesis.app.ui.screens.client


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.getKoin
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.ui.screens.client.messaging.GuildsScreen

class ClientRootScreen : Screen {
    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val genesisClient = koin.get<GenesisClient>()
        val navigator = LocalNavigator.currentOrThrow
        // restart the gateway connection and the views when the gateway needs to reconnect
//        genesisClient.gateway.on<Boolean>("GATEWAY_DISCONNECTED") {
//            navigator.replace(RootScreen()) // rootscreen instead of gatewayloadscreen incase bad token
//        }
        Navigator(GuildsScreen())
    }
}