package xyz.genesisapp.genesis.app.screens.Auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import xyz.genesisapp.genesis.app.screens.RootSharedScreen


class LoginScreen(
    private val isLogin: Boolean,
) : Screen {

    @Composable
    override fun Content() {

        val authNavigator = LocalNavigator.currentOrThrow
        val rootNavigator = authNavigator.parent!!

        val loadingScreen = rememberScreen(RootSharedScreen.Loading)


        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Scaffold { pv ->
            Box(
                modifier = androidx.compose.ui.Modifier
                    .padding(pv)
                    .fillMaxSize()

            ) {

                // Back
                // Put button in top left

                Box(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxSize()
                        .align(Alignment.TopStart)
                ) {
                    TextButton(
                        onClick = {
                            authNavigator.pop()
                        },
                    ) {
                        Text("<")
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        if (isLogin) "Login" else "Sign Up",
                    )

                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = {
                            Text("Username")
                        }
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text("Password")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        )
                    )

                    Button(
                        onClick = {
                            rootNavigator.push(loadingScreen)
                        }
                    ) {
                        Text(
                            if (isLogin) "Login" else "Sign Up",
                        )
                    }
                }

            }
        }

    }
}