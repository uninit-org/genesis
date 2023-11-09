package xyz.genesisapp.genesis.app.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.aakira.napier.Napier
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.genesis.app.ui.components.Centered
import xyz.genesisapp.genesis.app.ui.components.form.composeForm
import xyz.genesisapp.genesis.app.ui.screens.client.GatewayLoadScreen

class LoginScreen(

) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    override fun Content() {
        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        val genesisClient = koin.get<GenesisClient>()
        val navigator = LocalNavigator.currentOrThrow

        val scope = rememberCoroutineScope()

        var tokenPref by prefs.preference("auth.token", "")

        Centered {
            composeForm {
                /*
                twoSided {
                    left("password") {
                        head("Login")
                        text("login", "Login")
                        password("password", "Password")
                        submit("Login")
                        swap("Use token")
                    }
                    right("token") {
                        head("Login with token")
                        password("token", "Token")
                        submit("Login with token")
                        swap("Use password")
                    }
                }
                 */
                text("login", "Login")
                password("password", "Password")
                separator("separator", "Or, login with")
                password("token", "Token")
                switch("use-token", "Use token")
                separator("separator", "")
                switch("remember", "Remember me")
                assignSubmitText("Login")
                onSubmit {
                    val formResponse = this
                    scope.launch {
                        if (formResponse["use-token"] as Boolean) {
                            val token = formResponse["token"] as String
                            genesisClient.rest.token = token
                            val res = genesisClient.rest.getDomainMe()
                            if (res.isOk()) {
                                tokenPref = token
                                if (genesisClient.logLevel >= LogLevel.INFO) Napier.i(
                                    "Logged in as ${res.getOrNull()!!.username}",
                                    null,
                                    "Login"
                                )
                                navigator.replace(GatewayLoadScreen())
                            } else {
                                genesisClient.rest.token = ""
                                if (genesisClient.logLevel >= LogLevel.ERROR) Napier.e(
                                    "Invalid token",
                                    Throwable(res.errorOrNull()?.message),
                                    "Login"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}