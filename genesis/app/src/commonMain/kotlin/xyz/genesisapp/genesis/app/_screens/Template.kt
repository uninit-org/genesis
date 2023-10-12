package xyz.genesisapp.genesis.app._screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow


class TemplateScreen() : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        // Register any screens you intend to navigate to here
        val loadingScreen = rememberScreen(RootSharedScreen.Loading)

        Scaffold { pv ->
            Box(
                modifier = androidx.compose.ui.Modifier
                    .padding(pv)
                    .fillMaxSize()

            ) {

                Text("Template Screen")

                Button(
                    onClick = {
                        // Go back
                        navigator.pop()
                        // Go to a specific screen
                        navigator.push(loadingScreen)
                    },
                ) {
                    Text("Template Button")
                }


            }
        }

    }
}