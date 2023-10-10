package xyz.genesisapp.genesis.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.genesisapp.genesis.app.theme.ThemeProvider
import xyz.genesisapp.genesis.app.theme.builtin.Catppuccin
import xyz.genesisapp.genesis.app.views.LoginScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppShim(
    contents: @Composable () -> Unit = {}
) {
    ThemeProvider(Catppuccin.Mocha.Rosewater) {
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
                    contents()
                }
            }
        }

    }
}

@Composable
@Preview
fun Test() {
    AppShim {
        LoginScreen()
    }
}