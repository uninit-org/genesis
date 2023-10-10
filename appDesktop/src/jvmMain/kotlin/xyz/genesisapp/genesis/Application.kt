package xyz.genesisapp.genesis

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import xyz.genesisapp.genesis.app.MainView


class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            application {
                Window(
                    onCloseRequest = ::exitApplication,
                ) {
                    MainView()
                }
            }
        }
    }
}