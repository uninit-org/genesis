package uninit.genesis.app

import cafe.adriel.voyager.navigator.Navigator
import org.koin.core.Koin
import uninit.genesis.discord.client.GenesisClient
import uninit.genesis.app.data.DataStore
import uninit.genesis.app.di.dataStoreModule
import uninit.genesis.app.di.genesisApiModule
import uninit.genesis.app.di.genesisClientModule
import uninit.genesis.app.ui.screens.RootScreen

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