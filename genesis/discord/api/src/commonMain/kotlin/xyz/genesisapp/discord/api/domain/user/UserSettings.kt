package uninit.genesis.discord.api.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildFolder(
    val color: Long? = null,
    @SerialName("guild_ids")
    var guildIds: List<String>,
    val id: Long? = null,
    val name: String? = null,
    var collapsed: Boolean = true,
)

@Serializable
class UserSettings(
    @SerialName("guild_folders")
    var guildFolders: MutableList<GuildFolder>,
) {

}