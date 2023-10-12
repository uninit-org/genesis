package xyz.genesisapp.genesis

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import xyz.genesisapp.genesis.app.MainView
import xyz.genesisapp.genesis.app.platform.android.LocalApplicationContext

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalApplicationContext provides this.applicationContext
            ) {
                MainView()
            }
        }
    }
}