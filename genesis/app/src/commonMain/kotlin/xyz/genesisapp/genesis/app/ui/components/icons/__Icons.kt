package uninit.genesis.app.ui.components.icons

import androidx.compose.ui.graphics.vector.ImageVector
import uninit.genesis.app.ui.components.icons.icons.Empty
import uninit.genesis.app.ui.components.icons.icons.Textchannel
import uninit.genesis.app.ui.components.icons.icons.Thread
import kotlin.collections.List as ____KtList

public object Icons

private var __AllIcons: ____KtList<ImageVector>? = null

public val Icons.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Textchannel, Thread, Empty)
        return __AllIcons!!
    }
