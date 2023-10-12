package xyz.genesisapp.genesis.app.di

import androidx.compose.runtime.Composable
import org.koin.core.module.Module
import org.koin.dsl.module
import xyz.genesisapp.common.preferences.PreferencesManager
@Composable

actual fun preferencesModule(): Module {
    return module {
        single { PreferencesManager() }
    }
}