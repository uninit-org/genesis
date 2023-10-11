package xyz.genesisapp.genesis.app.screens.Auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import xyz.genesisapp.genesis.app.Components.BackArrow
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

                BackArrow()

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        if (isLogin) "Login" else "Sign Up",
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = {
                            Text("Username")
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

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

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            rootNavigator.push(loadingScreen)
                            authNavigator.pop()
                        }
                    ) {
                        Text(
                            if (isLogin) "Login" else "Sign Up",
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

            }
        }

    }
}

class TokenLoginScreen() : Screen {

    @Composable
    override fun Content() {
        val authNavigator = LocalNavigator.currentOrThrow
        val rootNavigator = authNavigator.parent!!

        val loadingScreen = rememberScreen(RootSharedScreen.Loading)

        var token by remember { mutableStateOf("") }

        Scaffold { pv ->
            Box(
                modifier = androidx.compose.ui.Modifier
                    .padding(pv)
                    .fillMaxSize()

            ) {

                BackArrow()

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text("Enter your token")

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = token,
                        onValueChange = { token = it },
                        label = {
                            Text("Token")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))


                    Button(
                        onClick = {
                            rootNavigator.push(loadingScreen)
                            authNavigator.pop()
                        }
                    ) {
                        Text("Login")
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}