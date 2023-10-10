package xyz.genesisapp.genesis.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Desktop"

@Preview
@Composable
fun MainView() {
    App()
}