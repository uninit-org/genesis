package xyz.genesisapp.genesis.app.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import xyz.genesisapp.genesis.app.theme.ThemeProvider

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen() {
    var isLogin by remember { mutableStateOf(false) }
    var isSplash by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    ThemeProvider {
        val colorscheme = this
        Scaffold(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
        ) { pv ->
            if (isSplash) {
                Box(
                    modifier = Modifier
                        .padding(pv)
                        .fillMaxSize()
                        .paint(
                            painterResource("images/img_landing_splash.png")
                        )

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Welcome to Genesis",
                            color = colorscheme.primary,
                        )

                        Button(
                            onClick = {
                                isLogin = true
                                isSplash = false
                                password = ""
                            }
                        ) {
                            Text(
                                text = "Login",
                                color = colorscheme.primary,
                            )
                        }

                        Button(
                            onClick = {
                                isLogin = false
                                isSplash = false
                                password = ""
                            }

                        ) {
                            Text(
                                text = "Sign Up",
                                color = colorscheme.primary,
                            )
                        }
                    }

                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(pv)
                        .fillMaxSize()

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            if (isLogin) "Login" else "Sign Up",
                            color = colorscheme.primary,
                        )

                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            label = {
                                Text(
                                    text = "Username",
                                    color = colorscheme.primary,
                                )
                            }
                        )

                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            label = {
                                Text(
                                    text = "Password",
                                    color = colorscheme.primary,
                                )
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
                                color = colorscheme.primary,
                            )
                        }
                    }

                }
            }
        }
    }
}