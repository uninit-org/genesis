package xyz.genesisapp.genesis.app.screens.Auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow


class LoginScreen(
    private val isLogin: Boolean,
) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

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
                            navigator.pop()
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
                        onClick = {}
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