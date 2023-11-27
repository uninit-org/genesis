package xyz.genesisapp.common.fytix

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
open class EventBus(val composableId: String = "") {

    interface EventListener<E> {
        val uuid: Int
        fun onEvent(event: E)
    }

    interface Event<E> {
        val type: String
        val data: E
    }

    @PublishedApi
    internal val busses: MutableMap<String, MutableList<EventListener<Event<*>>>> = mutableMapOf()

    @PublishedApi
    internal val byId: MutableMap<Int, EventListener<Event<*>>> = mutableMapOf()

    @PublishedApi
    internal var latestUuid: Int = 0

    fun <E : Any?> on(event: String, func: Event<E>.(E) -> Unit) {
        val listener = object : EventListener<Event<E>> {
            override val uuid: Int = latestUuid++
            override fun onEvent(event: Event<E>) = event.func(event.data)
        } as EventListener<Event<*>>
        if (busses.containsKey(event)) {
            busses[event]!!.add(listener)
        } else {
            busses[event] = mutableListOf(listener)
        }
        byId[listener.uuid] = listener
    }

    fun <A : Any?> emit(event: String, data: A) {
        if (!busses.containsKey(event)) return // TODO: Default ("UNKNOWN" event) event bus & "ALL" event
        CoroutineScope((Dispatchers.Default)).launch {
            val listeners = busses[event]!!
            for (listener in listeners) {
                withContext(Dispatchers.Default) {
                    listener.onEvent(object : Event<A> {
                        override val type: String = event
                        override val data: A = data
                    })
                }
            }
        }
    }

    fun <E : Any?> once(event: String, func: Event<E>.(E) -> Unit) {
        val listener = object : EventListener<Event<E>> {
            override val uuid: Int = latestUuid++
            override fun onEvent(event: Event<E>) {
                event.func(event.data)
                off(uuid)
            }
        } as EventListener<Event<*>>
        if (busses.containsKey(event)) {
            busses[event]!!.add(listener)
        } else {
            busses[event] = mutableListOf(listener)
        }
        byId[listener.uuid] = listener
    }

    suspend fun <E : Any?> waitFor(event: String): E {
        var result: E? = null
        once(event) {
            result = data
        }
        while (result == null) {
            delay(1)
        }
        return result!!
    }

    @PublishedApi
    internal fun off(uuid: Int) {
        val listener = byId[uuid] ?: return
        busses.values.indexOfFirst { it.remove(listener) }
        byId.remove(uuid)
    }

    @Composable
    inline fun <E : Any> compositionLocalOn(event: String, crossinline func: Event<E>.(E) -> Unit) {
        val listener = object : EventListener<Event<E>> {
            override val uuid: Int = latestUuid++
            override fun onEvent(event: Event<E>) = event.func(event.data)
        } as EventListener<Event<*>>
        DisposableEffect("EventBus#{{$composableId}}+${listener.uuid}") {
            if (busses.containsKey(event)) {
                busses[event]!!.add(listener)
            } else {
                busses[event] = mutableListOf(listener)
            }
            byId[listener.uuid] = listener
            onDispose {
                off(listener.uuid)
            }
        }
    }
}