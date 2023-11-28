package uninit.genesis.app.ui.screens.client.messaging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uninit.genesis.discord.client.entities.guild.Channel

@Composable
fun MemberList(channel: Channel) {
    Scaffold(
        modifier = Modifier.fillMaxHeight()
            .width(200.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        
    }
}