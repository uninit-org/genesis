package xyz.genesisapp.common

import xyz.genesisapp.common.platform.OsFamily

expect object Platform {

    val osFamily: OsFamily

    fun vibrate(iosIntensity: Float, iosSharpness: Float, androidTime: Int)
}