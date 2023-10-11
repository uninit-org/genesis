package xyz.genesisapp.genesis.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


class LandingScreen : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val registerScreen = rememberScreen(SharedScreen.Login(false))
        val loginScreen = rememberScreen(SharedScreen.Login(true))

        Scaffold { pv ->
            Box(
                modifier = androidx.compose.ui.Modifier
                    .padding(pv)
                    .fillMaxSize()
                    .paint(
                        painterResource("images/img_landing_splash.png")
                    )

            ) {

                // Back

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Welcome to Genesis",
                    )

                    Button(
                        onClick = {
                            navigator.push(loginScreen)
                        }
                    ) {
                        Text("Login")
                    }

                    Button(
                        onClick = {
                            navigator.push(registerScreen)
                        }

                    ) {
                        Text("Sign Up")
                    }
                }


            }

        }
    }

}