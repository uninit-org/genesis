package xyz.genesisapp.genesis.app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.common.getTimeInMillis
import xyz.genesisapp.discord.api.types.Snowflake

class DataStore {

    var currentGuild: Snowflake? = null

    var mobileUi by mutableStateOf(false)
    val shiggyEasterEgg by mutableStateOf(getTimeInMillis() % 10 == 0L)

    val events: EventEmitter = EventEmitter()
}