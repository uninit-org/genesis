package xyz.genesisapp.genesis.app.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import xyz.genesisapp.genesis.app.theme.builtin.Catppuccin


@Composable
fun ThemeProvider(theme: Theme = Catppuccin.Mocha.Rosewater, content: @Composable (ColorScheme.() -> Unit)) {
    MaterialTheme(
        colorScheme = theme.getColors(),
        typography = theme.getTypography(),
        shapes = theme.getShapes(),
    ) {
        theme.getColors().content()
    }
}