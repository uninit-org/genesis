package xyz.genesisapp.common

import xyz.genesisapp.common.platform.OsFamily

actual object Platform {
    actual val osFamily: OsFamily
        get() = OsFamily.IOS


    actual fun vibrate(iosIntensity: Float, iosSharpness: Float, androidTime: Int) {
    }

}