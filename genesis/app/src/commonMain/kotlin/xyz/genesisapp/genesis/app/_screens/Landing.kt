<<<<<<<< Updated upstream:genesis/app/src/commonMain/kotlin/xyz/genesisapp/genesis/app/_screens/Auth/Landing.kt
package xyz.genesisapp.genesis.app.screens.Auth
========
package xyz.genesisapp.genesis.app._screens
>>>>>>>> Stashed changes:genesis/app/src/commonMain/kotlin/xyz/genesisapp/genesis/app/_screens/Landing.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.unit.dp
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

        val registerScreen = rememberScreen(AuthSharedScreen.Login(false))
        val loginScreen = rememberScreen(AuthSharedScreen.Login(true))
        val tokenScreen = rememberScreen(AuthSharedScreen.TokenLogin)

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
                    verticalArrangement = Arrangement.Bottom,
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
                            navigator.push(tokenScreen)
                        }
                    ) {
                        Text("Login with token")
                    }

                    Button(
                        onClick = {
                            navigator.push(registerScreen)
                        }

                    ) {
                        Text("Sign Up")
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                }


            }

        }
    }

}