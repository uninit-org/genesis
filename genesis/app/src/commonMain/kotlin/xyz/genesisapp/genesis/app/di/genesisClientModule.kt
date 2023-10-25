package xyz.genesisapp.genesis.app.di

import org.koin.dsl.module
import xyz.genesisapp.discord.client.GenesisClient


fun genesisClientModule() = module {
    single { GenesisClient(get()) }
}