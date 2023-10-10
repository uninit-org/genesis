package xyz.genesisapp.genesis.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.genesisapp.genesis.app.views.LoginScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    LoginScreen()
}

expect fun getPlatformName(): String