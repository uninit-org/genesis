package xyz.genesisapp.genesis.app.di

import androidx.compose.runtime.Composable
import org.koin.core.module.Module

@Composable
fun initKoin(): Module {
    val prefsModule = preferencesModule()
    return prefsModule

}