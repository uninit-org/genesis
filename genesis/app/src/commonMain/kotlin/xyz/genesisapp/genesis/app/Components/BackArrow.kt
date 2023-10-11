package xyz.genesisapp.genesis.app.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun BoxScope.BackArrow(
    onClick: (() -> Unit)? = null,
    navigator: Navigator? = LocalNavigator.currentOrThrow,
) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .align(Alignment.TopStart)
    ) {
        TextButton(
            onClick = {
                if (onClick != null) {
                    onClick()
                } else {
                    navigator?.pop()
                }
            },
        ) {
            Text("<")
        }
    }
}