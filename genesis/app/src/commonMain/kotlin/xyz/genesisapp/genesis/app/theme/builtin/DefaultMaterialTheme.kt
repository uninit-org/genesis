package xyz.genesisapp.genesis.app.theme.builtin

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import xyz.genesisapp.genesis.app.theme.Theme

object DefaultMaterialTheme : Theme("Default Material Theme") {
    @Composable
    override fun getColors(): ColorScheme {
        return MaterialTheme.colorScheme
    }
}