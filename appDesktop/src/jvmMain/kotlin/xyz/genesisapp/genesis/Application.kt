package uninit.genesis

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import uninit.genesis.app.MainView


class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            application {
                content()
            }
        }
    }
}
@Composable
@Preview
fun ApplicationScope.content() {
    Window(onCloseRequest = ::exitApplication) {
        MainView()
    }
}