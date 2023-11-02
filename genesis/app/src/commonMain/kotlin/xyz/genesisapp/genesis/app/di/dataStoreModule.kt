package xyz.genesisapp.genesis.app.di

import org.koin.dsl.module
import xyz.genesisapp.genesis.app.data.DataStore


fun dataStoreModule() = module {
    single { DataStore() }
}