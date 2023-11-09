package xyz.genesisapp.genesis.app.ui.screens.client.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SettingsEntry(name: String, icon: String?, screen: Screen) {
    val navigator = LocalNavigator.currentOrThrow

    Row(
        modifier = Modifier
            .clickable {
                navigator.push(screen)
            }
            .fillMaxWidth()
            .height(48.dp)
            .padding(start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (icon != null) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
            )
        }
        Text(name, modifier = Modifier.padding(start = 16.dp))
    }
}