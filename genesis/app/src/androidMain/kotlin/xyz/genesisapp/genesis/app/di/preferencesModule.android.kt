package uninit.genesis.app.di

import androidx.compose.runtime.Composable
import org.koin.core.module.Module
import org.koin.dsl.module
import uninit.genesis.common.preferences.PreferencesManager
import uninit.genesis.app.platform.android.LocalApplicationContext

@Composable
actual fun preferencesModule(): Module {
    val context = LocalApplicationContext.current
    return module {
        single<PreferencesManager> {
            context?.let {
                return@single PreferencesManager(it.getSharedPreferences("genesis", 0))
            }
            throw IllegalStateException("Application context is not initialized")
        }
    }
}