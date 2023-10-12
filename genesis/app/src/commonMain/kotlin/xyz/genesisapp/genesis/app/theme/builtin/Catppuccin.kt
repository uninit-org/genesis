package xyz.genesisapp.genesis.app.theme.builtin

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import xyz.genesisapp.common.LinearGradient
import xyz.genesisapp.common.color
import xyz.genesisapp.common.linearGradient
import xyz.genesisapp.genesis.app.theme.Theme

object Catppuccin {
    internal fun flavorToTheme(
        name: String,
        flavor: Flavor,
        primaryColor: Color,
        secondaryColor: Color,
        tertiaryColor: Color,
        errorColor: Color
    ): Lazy<Theme> {
        return lazy {
            object : Theme("Catppuccin $name") {
                @Composable
                override fun getColors(): ColorScheme {
                    fun gradientFrom(color: Color): LinearGradient {
                        return@gradientFrom linearGradient(
                            color(LinearGradient.RGBA.BLACK, 0F),
                            color(
                                (color.red * 255f).toInt(),
                                (color.green * 255f).toInt(),
                                (color.blue * 255f).toInt(),
                                (color.alpha * 255f).toInt(),
                                40F
                            ),
                            color(LinearGradient.RGBA.WHITE, 100F)
                        )
                    }
                    fun LinearGradient.RGBA.color(): Color {
                        return@color Color(
                            this.r.coerceIn(0..255),
                            this.g.coerceIn(0..255),
                            this.b.coerceIn(0..255),
                            this.a.coerceIn(0..255)
                        )
                    }
                    val primary = gradientFrom(primaryColor)
                    val secondary = gradientFrom(secondaryColor)
                    val tertiary = gradientFrom(tertiaryColor)
                    val error = gradientFrom(errorColor)

                    val neutral = gradientFrom(flavor.Surface0)
                    val neutralVariant = gradientFrom(flavor.Surface1)
                    return darkColorScheme(
                        primary = flavor.Surface0,
                        onPrimary = flavor.Text,
                        primaryContainer = primary[90F].color(),
                        onPrimaryContainer = primary[10F].color(),
                        secondary = secondary[40F].color(),
                        onSecondary = secondary[100F].color(),
                        secondaryContainer = secondary[90F].color(),
                        onSecondaryContainer = secondary[10F].color(),
                        tertiary = tertiary[40F].color(),
                        onTertiary = tertiary[100F].color(),
                        tertiaryContainer = tertiary[90F].color(),
                        onTertiaryContainer = tertiary[10F].color(),
                        error = error[40F].color(),
                        onError = error[100F].color(),
                        errorContainer = error[90F].color(),
                        onErrorContainer = error[10F].color(),
                        surface = neutral[40F].color(),
                        onSurface = neutral[100F].color(),
                        surfaceVariant = neutralVariant[40F].color(),
                        onSurfaceVariant = neutralVariant[100F].color(),
                        background = flavor.Crust,
                        onBackground = flavor.Base,
                        surfaceTint = primary[40F].color(),

                    )
                }
            }
        }
    }

    sealed class Flavor(
        val Crust: Color,
        val Mantle: Color,
        val Base: Color,

        val Surface0: Color,
        val Surface1: Color,
        val Surface2: Color,

        val Overlay0: Color,
        val Overlay1: Color,
        val Overlay2: Color,

        val Subtext0: Color,
        val Subtext1: Color,

        val Text: Color,

        val Lavender: Color,
        val Blue: Color,
        val Sapphire: Color,
        val Sky: Color,
        val Teal: Color,
        val Green: Color,
        val Yellow: Color,
        val Peach: Color,
        val Maroon: Color,
        val Red: Color,
        val Mauve: Color,
        val Pink: Color,
        val Flamingo: Color,
        val Rosewater: Color,
    )

    sealed class ThemePack(name: String, flavor: Flavor) {
        val Lavender by flavorToTheme(
            "$name Lavender",
            flavor,
            flavor.Lavender,
            flavor.Sapphire,
            flavor.Teal,
            flavor.Red
        )
        val Blue by flavorToTheme("$name Blue", flavor, flavor.Blue, flavor.Sky, flavor.Green, flavor.Maroon)
        val Sapphire by flavorToTheme("$name Sapphire", flavor, flavor.Sapphire, flavor.Teal, flavor.Yellow, flavor.Red)
        val Sky by flavorToTheme("$name Sky", flavor, flavor.Sky, flavor.Green, flavor.Peach, flavor.Maroon)
        val Teal by flavorToTheme("$name Teal", flavor, flavor.Teal, flavor.Yellow, flavor.Maroon, flavor.Red)
        val Green by flavorToTheme("$name Green", flavor, flavor.Green, flavor.Peach, flavor.Red, flavor.Maroon)
        val Yellow by flavorToTheme("$name Yellow", flavor, flavor.Yellow, flavor.Maroon, flavor.Mauve, flavor.Red)
        val Peach by flavorToTheme("$name Peach", flavor, flavor.Peach, flavor.Red, flavor.Pink, flavor.Maroon)
        val Maroon by flavorToTheme("$name Maroon", flavor, flavor.Maroon, flavor.Mauve, flavor.Flamingo, flavor.Red)
        val Red by flavorToTheme("$name Red", flavor, flavor.Red, flavor.Pink, flavor.Rosewater, flavor.Maroon)
        val Mauve by flavorToTheme("$name Mauve", flavor, flavor.Mauve, flavor.Flamingo, flavor.Lavender, flavor.Red)
        val Pink by flavorToTheme("$name Pink", flavor, flavor.Pink, flavor.Rosewater, flavor.Blue, flavor.Maroon)
        val Flamingo by flavorToTheme(
            "$name Flamingo",
            flavor,
            flavor.Flamingo,
            flavor.Lavender,
            flavor.Sapphire,
            flavor.Red
        )
        val Rosewater by flavorToTheme(
            "$name Rosewater",
            flavor,
            flavor.Rosewater,
            flavor.Blue,
            flavor.Sky,
            flavor.Maroon
        )
    }

    @PublishedApi
    internal data object LatteFlavor : Flavor(
        Crust = Color(0xFFdce0e8),
        Mantle = Color(0xFFe6e9ef),
        Base = Color(0xFFeff1f5),

        Surface0 = Color(0xFFccd0da),
        Surface1 = Color(0xFFbcc0cc),
        Surface2 = Color(0xFFacb0be),

        Overlay0 = Color(0xFF9ca0b0),
        Overlay1 = Color(0xFF8c8fa1),
        Overlay2 = Color(0xFF7c7f93),

        Subtext0 = Color(0xFF6c6f85),
        Subtext1 = Color(0xFF5c5f77),

        Text = Color(0xFF4c4f69),

        Lavender = Color(0xFF7287fd),
        Blue = Color(0xFF1e66f5),
        Sapphire = Color(0xFF209fb5),
        Sky = Color(0xFF04a5e5),
        Teal = Color(0xFF179299),
        Green = Color(0xFF40a02b),
        Yellow = Color(0xFFdf8e1d),
        Peach = Color(0xFFfe640b),
        Maroon = Color(0xFFe64553),
        Red = Color(0xFFd20f39),
        Mauve = Color(0xFF8839ef),
        Pink = Color(0xFFea76cb),
        Flamingo = Color(0xFFdd7878),
        Rosewater = Color(0xFFdc8a78)
    )

    @PublishedApi
    internal data object FrappeFlavor : Flavor(
        Crust = Color(0xFF232634),
        Mantle = Color(0xFF292c3c),
        Base = Color(0xFF414559),

        Surface0 = Color(0xFF414559),
        Surface1 = Color(0xFF51576d),
        Surface2 = Color(0xFF626880),

        Overlay0 = Color(0xFF737994),
        Overlay1 = Color(0xFF838ba7),
        Overlay2 = Color(0xFF949cbb),

        Subtext0 = Color(0xFFa5adce),
        Subtext1 = Color(0xFFb5bfe2),

        Text = Color(0xFFc6d0f5),

        Lavender = Color(0xFFbabbf1),
        Blue = Color(0xFF8caaee),
        Sapphire = Color(0xFF85c1dc),
        Sky = Color(0xFF99d1db),
        Teal = Color(0xFF81c8be),
        Green = Color(0xFFa6d189),
        Yellow = Color(0xFFe5c890),
        Peach = Color(0xFFef9f76),
        Maroon = Color(0xFFea999c),
        Red = Color(0xFFe78284),
        Mauve = Color(0xFFca9ee6),
        Pink = Color(0xFFf4b8e4),
        Flamingo = Color(0xFFeebebe),
        Rosewater = Color(0xFFf2d5cf)
    )

    @PublishedApi
    internal object MacchiatoFlavor : Flavor(
        Crust = Color(0xFF181926),
        Mantle = Color(0xFF1e2030),
        Base = Color(0xFF24273a),

        Surface0 = Color(0xFF363a4f),
        Surface1 = Color(0xFF494d64),
        Surface2 = Color(0xFF5b6078),

        Overlay0 = Color(0xFF6e738d),
        Overlay1 = Color(0xFF8087a2),
        Overlay2 = Color(0xFF939ab7),

        Subtext0 = Color(0xFFa5adcb),
        Subtext1 = Color(0xFFb8c0e0),

        Text = Color(0xFFcad3f5),

        Lavender = Color(0xFFb7bdf8),
        Blue = Color(0xFF8aadf4),
        Sapphire = Color(0xFF7dc4e4),
        Sky = Color(0xFF91d7e3),
        Teal = Color(0xFF8bd5ca),
        Green = Color(0xFFa6da95),
        Yellow = Color(0xFFeed49f),
        Peach = Color(0xFFf5a97f),
        Maroon = Color(0xFFee99a0),
        Red = Color(0xFFed8796),
        Mauve = Color(0xFFc6a0f6),
        Pink = Color(0xFFf5bde6),
        Flamingo = Color(0xFFf0c6c6),
        Rosewater = Color(0xFFf4dbd6)
    )

    @PublishedApi
    internal object MochaFlavor : Flavor(
        Crust = Color(0xFF11111b),
        Mantle = Color(0xFF181825),
        Base = Color(0xFF1e1e2e),

        Surface0 = Color(0xFF313244),
        Surface1 = Color(0xFF45475a),
        Surface2 = Color(0xFF585b70),

        Overlay0 = Color(0xFF6c7086),
        Overlay1 = Color(0xFF7f849c),
        Overlay2 = Color(0xFF9399b2),

        Subtext0 = Color(0xFFa6adc8),
        Subtext1 = Color(0xFFbac2de),

        Text = Color(0xFFcdd6f4),

        Lavender = Color(0xFFb4befe),
        Blue = Color(0xFF89b4fa),
        Sapphire = Color(0xFF74c7ec),
        Sky = Color(0xFF89dceb),
        Teal = Color(0xFF94e2d5),
        Green = Color(0xFFa6e3a1),
        Yellow = Color(0xFFf9e2af),
        Peach = Color(0xFFfab387),
        Maroon = Color(0xFFeba0ac),
        Red = Color(0xFFf38ba8),
        Mauve = Color(0xFFcba6f7),
        Pink = Color(0xFFf5c2e7),
        Flamingo = Color(0xFFf2cdcd),
        Rosewater = Color(0xFFf5e0dc)
    )

    object Latte : ThemePack("Latte", LatteFlavor)
    object Frappe : ThemePack("Frappe", FrappeFlavor)
    object Macchiato : ThemePack("Macchiato", MacchiatoFlavor)
    object Mocha : ThemePack("Mocha", MochaFlavor)

    object Flavors {
        inline val Latte: Flavor
            get() = LatteFlavor
        inline val Frappe: Flavor
            get() = FrappeFlavor
        inline val Macchiato: Flavor
            get() = MacchiatoFlavor
        inline val Mocha: Flavor
            get() = MochaFlavor
    }

}