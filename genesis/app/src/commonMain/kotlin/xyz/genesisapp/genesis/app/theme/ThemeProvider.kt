package xyz.genesisapp.genesis.app.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import xyz.genesisapp.genesis.app.theme.builtin.Catppuccin

@Composable
fun ThemeProvider(theme: Theme = Catppuccin.Mocha.Rosewater, colorScheme: ColorScheme = theme.getColors(), content: @Composable (ColorScheme.() -> Unit)) {
    val LocalContextColors = compositionLocalOf { colorScheme }
    MaterialTheme(
        colorScheme = theme.getColors(),
        typography = theme.getTypography(),
        shapes = theme.getShapes(),
    ) {
        CompositionLocalProvider(
            LocalContextColors provides colorScheme
        ) {
            theme.getColors().content()
        }
    }
}