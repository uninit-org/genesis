package xyz.genesisapp.genesis.app.ui.screens.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import xyz.genesisapp.genesis.app.ui.components.Centered
import xyz.genesisapp.genesis.app.ui.components.form.composeForm

class LoginScreen(

) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen #$key") },
            onDisposed = { println("Navigator: Dispose screen #$key") }
        )

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
                setSubmitText("Login")
                onSubmit {

                }
            }
        }
    }
}