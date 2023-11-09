package xyz.genesisapp.genesis.app

import cafe.adriel.voyager.navigator.Navigator
import org.koin.core.Koin
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.genesis.app.data.DataStore
import xyz.genesisapp.genesis.app.di.dataStoreModule
import xyz.genesisapp.genesis.app.di.genesisApiModule
import xyz.genesisapp.genesis.app.di.genesisClientModule
import xyz.genesisapp.genesis.app.ui.screens.RootScreen

fun ReloadClient(koin: Koin, nav: Navigator) {
    var parent: Navigator = nav.parent!!
    while (parent.level != 0) {
        parent = parent.parent!!
    }

    val genesisClient = koin.get<GenesisClient>()
    genesisClient.gateway.disconnect()

    val oldDataStore = koin.get<DataStore>()

    koin.unloadModules(listOf(genesisClientModule(), genesisApiModule(), dataStoreModule()))
    koin.loadModules(listOf(genesisClientModule(), genesisApiModule(), dataStoreModule()))

    val newDataStore = koin.get<DataStore>()

    // Values that are necessary to transfer
    newDataStore.mobileUi = oldDataStore.mobileUi

    parent.replace(RootScreen())
}