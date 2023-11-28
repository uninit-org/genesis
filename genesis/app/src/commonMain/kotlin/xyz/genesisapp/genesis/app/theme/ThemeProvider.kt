package uninit.genesis.app.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import uninit.genesis.app.theme.builtin.AllThemes
import uninit.genesis.app.theme.builtin.Catppuccin

@Composable
fun ThemeProvider(
    theme: Theme = Catppuccin.Mocha.Rosewater,
    themeName: String? = null,
    colorScheme: ColorScheme = theme.getColors(), content: @Composable (ColorScheme.() -> Unit)
) {
    var computedTheme = theme
    if (themeName != null) {
        computedTheme = AllThemes.find { it.name == themeName } ?: computedTheme
    }
    MaterialTheme(
        colorScheme = computedTheme.getColors(),
        typography = computedTheme.getTypography(),
        shapes = computedTheme.getShapes(),
    ) {
        CompositionLocalProvider(
            LocalContextColors provides colorScheme
        ) {
            computedTheme.getColors().content()
        }
    }
}