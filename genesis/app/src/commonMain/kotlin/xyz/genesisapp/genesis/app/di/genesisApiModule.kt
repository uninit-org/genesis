package xyz.genesisapp.genesis.app.di

import org.koin.dsl.module
import xyz.genesisapp.genesisApi.GenesisApiClient


fun genesisApiModule() = module {
    single { GenesisApiClient(get()) }
}