package uninit.genesis.app.ui.components.icons.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import uninit.genesis.app.ui.components.icons.Icons

public val Icons.Textchannel: ImageVector
    get() {
        if (_textchannel != null) {
            return _textchannel!!
        }
        _textchannel = Builder(name = "Textchannel", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(5.887f, 21.0f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.493f, -0.587f)
                lineTo(6.0f, 17.0f)
                horizontalLineTo(2.595f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.492f, -0.586f)
                lineToRelative(0.175f, -1.0f)
                arcTo(0.5f, 0.5f, 0.0f, false, true, 2.77f, 15.0f)
                horizontalLineToRelative(3.58f)
                lineToRelative(1.06f, -6.0f)
                horizontalLineTo(4.005f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.492f, -0.586f)
                lineToRelative(0.175f, -1.0f)
                arcTo(0.5f, 0.5f, 0.0f, false, true, 4.18f, 7.0f)
                horizontalLineToRelative(3.58f)
                lineToRelative(0.637f, -3.587f)
                arcTo(0.5f, 0.5f, 0.0f, false, true, 8.889f, 3.0f)
                horizontalLineToRelative(0.984f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.493f, 0.587f)
                lineTo(9.76f, 7.0f)
                horizontalLineToRelative(6.0f)
                lineToRelative(0.637f, -3.587f)
                arcTo(0.5f, 0.5f, 0.0f, false, true, 16.889f, 3.0f)
                horizontalLineToRelative(0.984f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.493f, 0.587f)
                lineTo(17.76f, 7.0f)
                horizontalLineToRelative(3.405f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.492f, 0.586f)
                lineToRelative(-0.175f, 1.0f)
                arcTo(0.5f, 0.5f, 0.0f, false, true, 20.99f, 9.0f)
                horizontalLineToRelative(-3.58f)
                lineToRelative(-1.06f, 6.0f)
                horizontalLineToRelative(3.405f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.492f, 0.586f)
                lineToRelative(-0.175f, 1.0f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.492f, 0.414f)
                horizontalLineTo(16.0f)
                lineToRelative(-0.637f, 3.587f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.492f, 0.413f)
                horizontalLineToRelative(-0.984f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.493f, -0.587f)
                lineTo(14.0f, 17.0f)
                horizontalLineTo(8.0f)
                lineToRelative(-0.637f, 3.587f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.492f, 0.413f)
                horizontalLineToRelative(-0.984f)
                close()
                moveTo(9.41f, 9.0f)
                lineToRelative(-1.06f, 6.0f)
                horizontalLineToRelative(6.0f)
                lineToRelative(1.06f, -6.0f)
                horizontalLineToRelative(-6.0f)
                close()
            }
        }
        .build()
        return _textchannel!!
    }

private var _textchannel: ImageVector? = null
