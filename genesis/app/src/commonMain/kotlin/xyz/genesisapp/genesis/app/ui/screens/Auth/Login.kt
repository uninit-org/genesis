package xyz.genesisapp.genesis.app.ui.screens.Auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.api.domain.login.ApiLoginPayload
import xyz.genesisapp.discord.api.domain.login.ApiLoginResponse
import xyz.genesisapp.discord.api.domain.user.ApiUser
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.api.ApiOptions
import xyz.genesisapp.genesis.app.ui.components.BackArrow
import xyz.genesisapp.genesis.app.ui.screens.RootSharedScreen


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
        var failed by remember { mutableStateOf(false) }

        val koin = getKoin()

        val prefs = koin.get<PreferencesManager>()
        var tokenPref by remember { prefs.preference("auth.token", "") }

        val genesisClient = koin.get<GenesisClient>()

        val scope = rememberCoroutineScope()


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

                    if (failed) {


                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .width(200.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Invalid email or password",
                                color = Color.Red
                            )
                        }


                        Spacer(modifier = Modifier.height(10.dp))
                    }

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
                            // TODO: Password Authentication
                            scope.launch {
                                val response =
                                    genesisClient.api.post<ApiLoginPayload, ApiLoginResponse>(
                                        "/users/@me",
                                        ApiLoginPayload(
                                            login = username,
                                            password = password
                                        )
                                    )

                                if (response.httpCode == HttpStatusCode.OK) {
                                    tokenPref = response.data!!.ticket!!
                                    rootNavigator.push(loadingScreen)
                                } else if (response.httpCode == HttpStatusCode.BadRequest) {
                                    // hcaptcha
                                } else {
                                    // lmao have fun aenri
                                }
                            }
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
        var failed by remember { mutableStateOf(false) }

        val koin = getKoin()

        val prefs = koin.get<PreferencesManager>()
        var tokenPref by remember { prefs.preference("auth.token", "") }

        val genesisClient = koin.get<GenesisClient>()

        val scope = rememberCoroutineScope()

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

                    if (failed) {


                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .width(200.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Invalid token",
                                color = Color.Red
                            )
                        }


                        Spacer(modifier = Modifier.height(10.dp))
                    }


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
                            scope.launch {
                                val response = genesisClient.api.get<ApiUser>(
                                    "/users/@me", ApiOptions(
                                        token = token
                                    )
                                )

                                if (response.success) {
                                    tokenPref = token
                                    rootNavigator.push(loadingScreen)
                                } else {
                                    failed = true
                                }
                            }

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