package uninit.genesis.app.ui.screens.client.settings.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.compose.getKoin
import uninit.genesis.app.ReloadClient
import uninit.genesis.app.ui.components.icons.Icons
import uninit.genesis.app.ui.components.icons.icons.Empty

internal object GenesisSettings : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Empty)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Genesis",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val koin = getKoin()
        val navigator = LocalNavigator.currentOrThrow
        LazyColumn {

            item {
                Button(onClick = {
                    ReloadClient(koin, navigator)
                }) {
                    Text("Reload")
                }
            }
        }

    }
}