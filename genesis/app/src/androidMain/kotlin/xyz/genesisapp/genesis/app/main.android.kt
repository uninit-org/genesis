package xyz.genesisapp.genesis.app

import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Android"

@Composable
fun MainView() = App()
