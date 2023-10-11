package xyz.genesisapp.genesis.app.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingScreen : Screen {

    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val authNav = rememberScreen(RootSharedScreen.Auth)

        Text("Loading")

        GlobalScope.launch {
            delay(5000)
            navigator.push(authNav)
        }
    }
}