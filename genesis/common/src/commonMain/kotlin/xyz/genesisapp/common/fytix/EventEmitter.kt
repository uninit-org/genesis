package xyz.genesisapp.common.fytix

import kotlinx.coroutines.delay

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

    suspend fun <T> suspendOnce(event: String): T {
        var response: T? = null
        val listener: Listener<T> = object : Listener<T> {
            override val isOnce: Boolean = true
            override val callback: (T) -> Unit = { data ->
                println("callback")
                response = data
            }
        }
        if (listeners.containsKey(event)) {
            listeners[event]!!.add(listener)
        } else {
            listeners[event] = mutableListOf(listener)
        }

        while (response == null) {
            delay(1)
        }
        return response!!

    }


    @Suppress("UNCHECKED_CAST")
    fun <T> emit(event: String, data: T) {
        if (listeners.containsKey(event)) {
            listeners[event]!!.forEach {
                val listener = it as Listener<T>
                try {
                    listener.callback(data)
                } catch (e: Exception) {
                    println("Error in event listener for event $event")
                    e.printStackTrace()
                }
                if (listener.isOnce) {
                    listeners[event]!!.remove(listener)
                }
            }
        }
    }
}