package xyz.genesisapp.discord.client.flow

import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.Koin
import xyz.genesisapp.common.preferences.CommonMultiplatformPreferencesManager as PreferencesManager

class AuthFlow(private val koin: Koin, private val http: HttpClient, coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {
    val preferences: PreferencesManager by koin.inject()
    init {

        coroutineScope.launch {

        }


    }

}