package xyz.genesisapp.genesis.app.data

import xyz.genesisapp.common.fytix.EventEmitter
import xyz.genesisapp.discord.api.types.Snowflake

class DataStore {

    var currentGuild: Snowflake? = null

    val events: EventEmitter = EventEmitter()
}