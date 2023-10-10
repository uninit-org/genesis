package xyz.genesisapp.genesis.core.theme.builtin

import androidx.compose.material.Colors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import xyz.genesisapp.genesis.core.theme.Theme

object DefaultMaterialTheme: Theme() {
    @Composable
    override fun getColors(): ColorScheme {
        return MaterialTheme.colorScheme
    }
}