package uninit.genesis.app.di

import org.koin.dsl.module
import uninit.genesis.discord.client.GenesisClient


fun genesisClientModule() = module {
    single { GenesisClient(get(), enableAssetCache = true) }
}