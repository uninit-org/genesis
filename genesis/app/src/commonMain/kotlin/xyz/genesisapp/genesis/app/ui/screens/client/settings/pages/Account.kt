package xyz.genesisapp.genesis.app.ui.screens.client.settings.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object AccountSettings : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Account"
        )

    @Composable
    override fun Content() {
        println("acc settings")
        Text("account")
    }
}