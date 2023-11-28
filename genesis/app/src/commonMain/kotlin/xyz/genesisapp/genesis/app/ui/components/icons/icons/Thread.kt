package uninit.genesis.app.ui.components.icons.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import uninit.genesis.app.ui.components.icons.Icons

public val Icons.Thread: ImageVector
    get() {
        if (_thread != null) {
            return _thread!!
        }
        _thread = Builder(name = "Thread", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(5.433f, 21.0f)
                arcToRelative(0.12f, 0.12f, 0.0f, false, true, -0.118f, -0.141f)
                lineTo(6.0f, 17.0f)
                horizontalLineTo(2.143f)
                arcToRelative(0.12f, 0.12f, 0.0f, false, true, -0.118f, -0.14f)
                lineToRelative(0.308f, -1.76f)
                arcToRelative(0.12f, 0.12f, 0.0f, false, true, 0.118f, -0.1f)
                horizontalLineTo(6.35f)
                lineToRelative(1.06f, -6.0f)
                horizontalLineTo(3.553f)
                arcToRelative(0.12f, 0.12f, 0.0f, false, true, -0.118f, -0.14f)
                lineToRelative(0.308f, -1.76f)
                arcTo(0.12f, 0.12f, 0.0f, false, true, 3.86f, 7.0f)
                horizontalLineTo(7.76f)
                lineToRelative(0.692f, -3.901f)
                arcTo(0.12f, 0.12f, 0.0f, false, true, 8.57f, 3.0f)
                horizontalLineToRelative(1.757f)
                arcToRelative(0.12f, 0.12f, 0.0f, false, true, 0.118f, 0.141f)
                lineTo(9.76f, 7.0f)
                horizontalLineToRelative(6.0f)
                lineToRelative(0.692f, -3.901f)
                arcTo(0.12f, 0.12f, 0.0f, false, true, 16.57f, 3.0f)
                horizontalLineToRelative(1.757f)
                arcToRelative(0.12f, 0.12f, 0.0f, false, true, 0.118f, 0.141f)
                lineTo(17.76f, 7.0f)
                horizontalLineToRelative(3.857f)
                arcToRelative(0.12f, 0.12f, 0.0f, false, true, 0.118f, 0.14f)
                lineToRelative(-0.308f, 1.76f)
                arcToRelative(0.12f, 0.12f, 0.0f, false, true, -0.118f, 0.1f)
                horizontalLineToRelative(-3.9f)
                lineToRelative(-0.36f, 2.04f)
                horizontalLineTo(15.05f)
                lineTo(15.41f, 9.0f)
                horizontalLineToRelative(-6.0f)
                lineToRelative(-1.06f, 6.0f)
                horizontalLineToRelative(2.21f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(8.0f)
                lineToRelative(-0.693f, 3.901f)
                arcTo(0.12f, 0.12f, 0.0f, false, true, 7.19f, 21.0f)
                horizontalLineTo(5.433f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(13.44f, 12.96f)
                arcToRelative(0.96f, 0.96f, 0.0f, false, false, -0.96f, 0.96f)
                verticalLineToRelative(6.301f)
                curveToRelative(0.0f, 0.53f, 0.43f, 0.96f, 0.96f, 0.96f)
                horizontalLineToRelative(0.96f)
                arcToRelative(0.24f, 0.24f, 0.0f, false, true, 0.24f, 0.24f)
                verticalLineToRelative(2.039f)
                arcToRelative(0.24f, 0.24f, 0.0f, false, false, 0.4f, 0.178f)
                lineToRelative(2.446f, -2.21f)
                arcToRelative(0.96f, 0.96f, 0.0f, false, true, 0.643f, -0.247f)
                horizontalLineToRelative(4.43f)
                curveToRelative(0.531f, 0.0f, 0.96f, -0.43f, 0.96f, -0.96f)
                verticalLineTo(13.92f)
                arcToRelative(0.96f, 0.96f, 0.0f, false, false, -0.96f, -0.96f)
                horizontalLineToRelative(-9.12f)
                close()
            }
        }
        .build()
        return _thread!!
    }

private var _thread: ImageVector? = null
