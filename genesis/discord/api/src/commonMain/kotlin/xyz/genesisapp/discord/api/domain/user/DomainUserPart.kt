package xyz.genesisapp.discord.api.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import xyz.genesisapp.discord.api.types.Asset

@Serializable
data class DomainUserPart(
    val id: String,
    val username: String,

    @SerialName("global_name")
    val globalName: String,

    val avatar: Asset,

    @SerialName("avatar_decoration_data")
    val avatarDecorationData: JsonElement? = null,

    val discriminator: String,

    @SerialName("public_flags")
    val publicFlags: Long,

    val flags: Long,
    val banner: Asset,

    @SerialName("banner_color")
    val bannerColor: String? = null,

    @SerialName("accent_color")
    val accentColor: Int? = null,

    val bio: String
)