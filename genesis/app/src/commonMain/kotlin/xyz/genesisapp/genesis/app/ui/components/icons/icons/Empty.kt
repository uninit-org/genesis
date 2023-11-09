package xyz.genesisapp.genesis.app.ui.components.icons.icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.unit.dp
import xyz.genesisapp.genesis.app.ui.components.icons.Icons

public val Icons.Empty: ImageVector
    get() {
        if (_empty != null) {
            return _empty!!
        }
        _empty = Builder(
            name = "Empty", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
        }
            .build()
        return _empty!!
    }

private var _empty: ImageVector? = null
