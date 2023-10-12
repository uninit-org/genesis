package xyz.genesisapp.genesis.app.di

import androidx.compose.runtime.Composable
import org.koin.core.module.Module
import org.koin.dsl.module
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.genesis.app.platform.android.LocalApplicationContext

@Composable
actual fun preferencesModule(): Module {
    return module {
        single<PreferencesManager> {
            LocalApplicationContext.current?.let {
                return@single PreferencesManager(it.getSharedPreferences("genesis", 0))
            }
            throw IllegalStateException("Application context is not initialized")
        }
    }
}