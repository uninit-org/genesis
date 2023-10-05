package xyz.genesisapp.genesis

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import xyz.genesisapp.genesis.core.MainView

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MainView()
    }
}