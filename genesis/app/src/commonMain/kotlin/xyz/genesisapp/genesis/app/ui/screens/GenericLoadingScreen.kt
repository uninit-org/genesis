package xyz.genesisapp.genesis.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import xyz.genesisapp.genesis.app.ui.components.Centered

open class GenericLoadingScreen (
    val loadingText: String,
    val loadingProcedure: suspend () -> Screen
    ) : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        Centered {
            Image(
                painterResource("images/img_logo.png"),
                contentDescription = "Genesis Logo",
                modifier = Modifier.size(100.dp)
            )
            Text(loadingText)
        }
        val nav = LocalNavigator.currentOrThrow

        rememberCoroutineScope().launch {
            delay(100)
            nav.replace(loadingProcedure())
        }
    }
}