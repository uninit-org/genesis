package xyz.genesisapp.genesis.app.ui.components.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import xyz.genesisapp.discord.api.types.AssetType
import xyz.genesisapp.discord.api.types.toUrl
import xyz.genesisapp.discord.client.entities.guild.User

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Avatar(author: User, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(32.dp)
            .height(32.dp)
            .clip(shape = CircleShape)
    ) {
        if (author.avatar != null) {
            KamelImage(
                resource = asyncPainterResource(
                    author.avatar!!.toUrl(
                        AssetType.Avatar,
                        author.id,
                        128
                    )
                ),
                contentDescription = "Avatar",
            )
        } else {
            Image(
                painter = painterResource("images/default/default_avatar_${author.id % 5}.png"),
                contentDescription = "Avatar",
            )
        }
    }
}