package xyz.genesisapp.common.preferences

import xyz.genesisapp.common.interfaces.IDynValue

fun <T> IDynValue<T>.toPreference(key: String, defaultValue: T): Preference<T> {
    var t by this
    return Preference(key, defaultValue, { _, _ ->
        return@Preference t
    }, { v ->
        t = v
    })
}