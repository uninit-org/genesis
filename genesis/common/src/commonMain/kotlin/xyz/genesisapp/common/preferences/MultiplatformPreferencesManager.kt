package xyz.genesisapp.common.preferences

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

abstract class CommonMultiplatformPreferencesManager {
    class Preference<T>(
        private val key: String,
        private val defaultValue: T,
        private val getter: (String, T) -> T,
        private val setter: (T) -> Unit,
        internal val state: MutableState<T> = mutableStateOf(getter(key, defaultValue))
    ) : MutableState<T> by state {
        override var value: T
            get() = state.component1()
            set(value) = component2().invoke(value)
        override fun component1(): T {
            return value
        }
        override fun component2(): (T) -> Unit {
            return {
                state.component2().invoke(it)
                setter(it)
            }
        }

        operator fun getValue(thisRef: Any?, property: Any?): T {
            return value
        }
        operator fun setValue(thisRef: Any?, property: Any?, value: T) {
            this.value = value
        }
    }

    abstract fun preference(
        key: String,
        defaultValue: String,
    ): Preference<String>
    abstract fun preference(
        key: String,
        defaultValue: Int,
    ): Preference<Int>
    abstract fun preference(
        key: String,
        defaultValue: Long,
    ): Preference<Long>
    abstract fun preference(
        key: String,
        defaultValue: Float,
    ): Preference<Float>
    abstract fun preference(
        key: String,
        defaultValue: Boolean,
    ): Preference<Boolean>
    abstract fun preference(
        key: String,
        defaultValue: Set<String>,
    ): Preference<Set<String>>
}


expect class PreferencesManager : CommonMultiplatformPreferencesManager