package xyz.genesisapp.genesis.app.ui.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import xyz.genesisapp.common.fytix.EventEmitter

abstract class EventScreen : Screen {
    private var listeners: List<() -> Unit> = emptyList()

    @Composable
    fun Events(vararg events: EventEmitter.quietResponse) {
        LifecycleEffect(
            onStarted = {
                listeners = events.map {
                    it.eventEmitter.quietOn(it)
                }
            },
            onDisposed = {
                listeners.forEach { it() }
            }
        )
    }
}

abstract class EventTab : Tab {
    private var listeners: List<() -> Unit> = emptyList()

    @Composable
    fun Events(vararg events: EventEmitter.quietResponse) {
        LifecycleEffect(
            onStarted = {
                listeners = events.map {
                    it.eventEmitter.quietOn(it)
                }
            },
            onDisposed = {
                listeners.forEach { it() }
            }
        )
    }
}