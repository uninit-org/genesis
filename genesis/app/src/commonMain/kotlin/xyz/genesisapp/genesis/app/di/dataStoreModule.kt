package uninit.genesis.app.di

import org.koin.dsl.module
import uninit.genesis.app.data.DataStore


fun dataStoreModule() = module {
    single { DataStore() }
}