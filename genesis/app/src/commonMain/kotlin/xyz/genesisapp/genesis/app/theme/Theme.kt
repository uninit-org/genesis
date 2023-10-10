package xyz.genesisapp.genesis.app.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import xyz.genesisapp.genesis.app.theme.loader.ColorSchemeSerializable

abstract class Theme(public val name: String) {
    @Composable
    abstract fun getColors(): ColorScheme

    @Composable
    fun getTypography(): Typography {
        return MaterialTheme.typography
    }

    @Composable
    fun getShapes(): Shapes {
        return MaterialTheme.shapes
    }

    companion object {
        fun fromCS(name: String, colors: ColorSchemeSerializable): Theme {
            return object : Theme(name) {
                @Composable
                override fun getColors(): ColorScheme {
                    return ColorScheme(
                        colors.primary,
                        colors.onPrimary,
                        colors.primaryContainer,
                        colors.onPrimaryContainer,
                        colors.inversePrimary,
                        colors.secondary,
                        colors.onSecondary,
                        colors.secondaryContainer,
                        colors.onSecondaryContainer,
                        colors.tertiary,
                        colors.onTertiary,
                        colors.tertiaryContainer,
                        colors.onTertiaryContainer,
                        colors.background,
                        colors.onBackground,
                        colors.surface,
                        colors.onSurface,
                        colors.surfaceVariant,
                        colors.onSurfaceVariant,
                        colors.surfaceTint,
                        colors.inverseSurface,
                        colors.inverseOnSurface,
                        colors.error,
                        colors.onError,
                        colors.errorContainer,
                        colors.onErrorContainer,
                        colors.outline,
                        colors.outlineVariant,
                        colors.scrim,
                    )
                }
            }

        }

    }
}