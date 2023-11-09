package xyz.genesisapp.genesis.app.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.compose.getKoin
import xyz.genesisapp.genesis.app.data.DataStore

@Composable
fun BackButton() {
    val koin = getKoin()
    val dataStore = koin.get<DataStore>()

}