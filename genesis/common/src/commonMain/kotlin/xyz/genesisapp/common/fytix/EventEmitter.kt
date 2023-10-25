package xyz.genesisapp.common.fytix

open class EventEmitter {
    val listeners: MutableMap<String, MutableList<Listener<*>>> = mutableMapOf()

    val events: MutableMap<String, Any> = mutableMapOf()

    interface Listener<T> {
        val isOnce: Boolean
        val callback: (T) -> Unit
    }

    fun <T> on(event: String, callback: (T) -> Unit) {
        val listener: Listener<T> = object : Listener<T> {
            override val isOnce: Boolean = false
            override val callback: (T) -> Unit = callback
        }
        if (listeners.containsKey(event)) {
            listeners[event]!!.add(listener)
        } else {
            listeners[event] = mutableListOf(listener)
        }
    }

    fun <T> once(event: String, callback: (T) -> Unit) {
        val listener: Listener<T> = object : Listener<T> {
            override val isOnce: Boolean = true
            override val callback: (T) -> Unit = callback
        }
        if (listeners.containsKey(event)) {
            listeners[event]!!.add(listener)
        } else {
            listeners[event] = mutableListOf(listener)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> emit(event: String, data: T) {
        if (listeners.containsKey(event)) {
            listeners[event]!!.forEach {
                val listener = it as Listener<T>
                listener.callback(data)
                if (listener.isOnce) {
                    listeners[event]!!.remove(listener)
                }
            }
        }
    }
}