package uninit.genesis.app.di

import org.koin.dsl.module
import uninit.genesisApi.GenesisApiClient


fun genesisApiModule() = module {
    single { GenesisApiClient(get()) }
}