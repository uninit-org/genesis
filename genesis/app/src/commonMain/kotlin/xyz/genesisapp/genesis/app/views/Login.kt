package xyz.genesisapp.genesis.app.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import xyz.genesisapp.genesis.app.GlobalState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen(globalState: GlobalState, pv: PaddingValues) {
    var isLogin by remember { mutableStateOf(false) }
    var isSplash by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                    color = globalState.currentTheme.getColors().primary,
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
                        color = globalState.currentTheme.getColors().primary,
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
                        color = globalState.currentTheme.getColors().primary,
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
                    color = globalState.currentTheme.getColors().primary,
                )

                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = {
                        Text(
                            text = "Username",
                            color = globalState.currentTheme.getColors().primary,
                        )
                    }
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(
                            text = "Password",
                            color = globalState.currentTheme.getColors().primary,
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
                        color = globalState.currentTheme.getColors().primary,
                    )
                }
            }

        }
    }
}