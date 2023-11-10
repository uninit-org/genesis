package xyz.genesisapp.genesis.app.ui.components.guild

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import xyz.genesisapp.discord.client.entities.guild.Attachment
import xyz.genesisapp.discord.client.entities.guild.AttachmentType
import xyz.genesisapp.discord.client.entities.guild.Embed
import xyz.genesisapp.discord.client.entities.guild.EmbedType
import xyz.genesisapp.discord.client.entities.guild.Message
import xyz.genesisapp.genesis.app.ui.components.User.Avatar

@Composable
fun messageEmbed(embed: Embed) {
    when (embed.type) {
        EmbedType.IMAGE ->
            KamelImage(
                asyncPainterResource(embed.image!!.displayUrl),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(embed.image!!.height?.dp ?: 100.dp)
                    .width(embed.image!!.width?.dp ?: 100.dp),
                contentDescription = null
            )

        else -> return
    }
}

@Composable
fun messageAttachment(attachment: Attachment) {
    when (attachment.type) {
        AttachmentType.IMAGE ->
            KamelImage(
                asyncPainterResource(attachment.proxyUrl),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(attachment.height!!.dp)
                    .width(attachment.width!!.dp),
                contentDescription = null
            )

        else -> return
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun message(message: Message) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Avatar(message.author)
        Column {
            Text(message.author.displayName)
            Text(
                message.content,
                color = if (message.isSent) MaterialTheme.colorScheme.onSurface else Color.Gray
            )
            message.attachments.forEach { messageAttachment(it) }
            message.embeds.forEach { messageEmbed(it) }
        }
    }
}