package uninit.genesis.app.ui.components.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import io.kamel.image.KamelImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import uninit.genesis.discord.client.entities.guild.User

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Avatar(author: User, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(32.dp)
            .height(32.dp)
            .clip(shape = CircleShape)
    ) {
        val defaultAvatar = painterResource("images/defaults/default_avatar_${author.id % 5}.png")
        if (author.avatar != null) {
            KamelImage(
                resource = author.avatar!!.render(128),
                contentDescription = "Avatar",
                onLoading = {
                    Image(
                        painter = defaultAvatar,
                        contentDescription = "Avatar Loading",
                    )
                },
                onFailure = {
                    Napier.e(
                        "Avatar for ${author.displayName} failed to load",
                        it,
                        "Avatar Component"
                    )
                    Image(
                        painter = defaultAvatar,
                        contentDescription = "Avatar Failed to load",
                    )
                }
            )
        } else {
            Image(
                painter = defaultAvatar,
                contentDescription = "Avatar",
            )
        }
    }
}