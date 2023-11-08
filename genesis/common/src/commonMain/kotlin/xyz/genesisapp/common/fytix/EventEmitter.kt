package xyz.genesisapp.common.fytix

import kotlinx.coroutines.delay

open class EventEmitter {
    private val listeners: MutableMap<String, MutableList<Listener<*>>> = mutableMapOf()

    val events: MutableMap<String, Any> = mutableMapOf()

    private var latestUuid: Int = 0

    interface Listener<T> {
        val isOnce: Boolean
        val callback: (T) -> Unit
        var uuid: Int
    }

    fun <T> on(event: String, callback: (T) -> Unit): () -> Unit {
        latestUuid++
        val listener: Listener<T> = object : Listener<T> {
            override val isOnce: Boolean = false
            override val callback: (T) -> Unit = callback
            override var uuid: Int = latestUuid
        }
        if (listeners.containsKey(event)) {
            listeners[event]!!.add(listener)
        } else {
            listeners[event] = mutableListOf(listener)
        }
        return {
            off(listener.uuid)
        }
    }

    class quietResponse(
        val event: String,
        val listener: Listener<*>,
        val eventEmitter: EventEmitter
    )

    fun <T : Any> quietRegister(event: String, callback: (T) -> Unit) =
        quietResponse(event, object : Listener<T> {
            override val isOnce: Boolean = false
            override val callback: (T) -> Unit = callback
            override var uuid: Int = -1
        }, this)


    fun quietOn(listener: quietResponse): () -> Unit {
        latestUuid++
        listener.listener.uuid = latestUuid
        if (listeners.containsKey(listener.event)) {
            listeners[listener.event]!!.add(listener.listener)
        } else {
            listeners[listener.event] = mutableListOf(listener.listener)
        }
        return {
            off(listener.listener.uuid)
        }
    }

    fun <T> once(event: String, callback: (T) -> Unit): () -> Unit {
        latestUuid++
        val listener: Listener<T> = object : Listener<T> {
            override val isOnce: Boolean = true
            override val callback: (T) -> Unit = callback
            override var uuid: Int = latestUuid
        }
        if (listeners.containsKey(event)) {
            listeners[event]!!.add(listener)
        } else {
            listeners[event] = mutableListOf(listener)
        }
        return {
            off(listener.uuid)
        }
    }

    fun off(uuid: Int) {
        val iterator = listeners.iterator()
        while (iterator.hasNext()) {
            val event = iterator.next()
            if (event.value.find { it.uuid == uuid } != null) {
                val iterator2 = event.value.iterator()
                while (iterator2.hasNext()) {
                    val listener = iterator2.next()
                    if (listener.uuid == uuid) {
                        iterator2.remove()
                    }
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
            override var uuid: Int = -1
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