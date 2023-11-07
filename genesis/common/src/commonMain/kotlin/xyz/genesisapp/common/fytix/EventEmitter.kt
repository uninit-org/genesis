package xyz.genesisapp.common.fytix

import kotlinx.coroutines.delay

open class EventEmitter {
    private val listeners: MutableMap<String, MutableList<Listener<*>>> = mutableMapOf()

    val events: MutableMap<String, Any> = mutableMapOf()

    private var latestUuid: Int = 0

    interface Listener<T> {
        val isOnce: Boolean
        val callback: (T) -> Unit
        val uuid: Int
    }

    fun <T> on(event: String, callback: (T) -> Unit): Int {
        latestUuid++
        val listener: Listener<T> = object : Listener<T> {
            override val isOnce: Boolean = false
            override val callback: (T) -> Unit = callback
            override val uuid: Int = latestUuid
        }
        if (listeners.containsKey(event)) {
            listeners[event]!!.add(listener)
        } else {
            listeners[event] = mutableListOf(listener)
        }
        return latestUuid
    }

    fun <T> once(event: String, callback: (T) -> Unit): Int {
        latestUuid++
        val listener: Listener<T> = object : Listener<T> {
            override val isOnce: Boolean = true
            override val callback: (T) -> Unit = callback
            override val uuid: Int = latestUuid
        }
        if (listeners.containsKey(event)) {
            listeners[event]!!.add(listener)
        } else {
            listeners[event] = mutableListOf(listener)
        }
        return latestUuid
    }

    fun off(uuid: Int) {
        val iterator = listeners.iterator()
        while (iterator.hasNext()) {
            val event = iterator.next()
            val iterator2 = event.value.iterator()
            while (iterator2.hasNext()) {
                val listener = iterator2.next()
                if (listener.uuid == uuid) {
                    listeners[event.key]!!.remove(listener)
                }
            }
        }
    }

    suspend fun <T> suspendOnce(event: String): T {
        var response: T? = null
        val listener: Listener<T> = object : Listener<T> {
            override val isOnce: Boolean = true
            override val callback: (T) -> Unit = { data ->
                response = data
            }
            override val uuid: Int = -1
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
            val iterator = listeners[event]!!.iterator()
            while (iterator.hasNext()) {
                val listener = iterator.next() as Listener<T>
                try {
                    listener.callback(data)
                } catch (e: Exception) {
                    println("Error in event listener for event $event")
                    e.printStackTrace()
                }
                if (listener.isOnce) {
                    iterator.remove()
                }
            }
        }
    }
}