package uninit.genesis.app.di

import androidx.compose.runtime.Composable
import org.koin.core.module.Module
import org.koin.dsl.module
import uninit.common.compose.preferences.PreferencesManager

@Composable
actual fun preferencesModule(): Module {
    return module {
        single {
            PreferencesManager(".genesis.json")
        }
    }
}