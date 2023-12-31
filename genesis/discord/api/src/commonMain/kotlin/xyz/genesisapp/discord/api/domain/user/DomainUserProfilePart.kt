package uninit.genesis.discord.api.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uninit.genesis.discord.api.types.Asset

@Serializable
data class DomainUserProfilePart(
    val bio: String,

    @SerialName("accent_color")
    val accentColor: Int? = null,

    val pronouns: String,

    @SerialName("profile_effect")
    val profileEffect: JsonElement? = null,

    val banner: Asset,

    @SerialName("theme_colors")
    val themeColors: List<Long>,

    @SerialName("popout_animation_particle_type")
    val popoutAnimationParticleType: JsonElement? = null,

    val emoji: JsonElement? = null
)