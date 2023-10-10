package xyz.genesisapp.genesis.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.theme.builtin.Catppuccin

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    ThemeProvider(Catppuccin.Mocha.Rosewater) {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showOther by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
        ) { pv ->
            Box(
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        showOther = !showOther
                    }) {
                        Text("hiiii")
                    }
                    AnimatedVisibility(showOther) {
                        Text("meowmeow >w<")
                    }
                }
            }
        }

    }
}

expect fun getPlatformName(): String