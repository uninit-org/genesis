package uninit.genesis.discord.entities.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import uninit.genesis.discord.api.domain.user.ApiUser
import uninit.genesis.discord.api.types.Asset
import uninit.genesis.discord.api.types.Snowflake

@Serializable
enum class ChannelType {
    @SerialName("0")
    GUILD_TEXT,

    @SerialName("1")
    DM,

    @SerialName("2")
    GUILD_VOICE,

    @SerialName("3")
    GROUP_DM,

    @SerialName("4")
    GUILD_CATEGORY,

    @SerialName("5")
    GUILD_NEWS,

    @SerialName("6")
    GUILD_STORE,

    @SerialName("7")
    GUILD_LFG,

    @SerialName("8")
    LFG_GROUP_DM,

    @SerialName("9")
    THREAD_ALPHA,

    @SerialName("10")
    NEWS_THREAD,

    @SerialName("11")
    PUBLIC_THREAD,

    @SerialName("12")
    PRIVATE_THREAD,

    @SerialName("13")
    GUILD_STAGE_VOICE,

    @SerialName("14")
    GUILD_DIRECTORY,

    @SerialName("15")
    GUILD_FORUM,

    @SerialName("16")
    GUILD_MEDIA
}

@Serializable
class ApiChannel(
    val id: Snowflake,
    @SerialName("guild_id")
    val guildId: Snowflake? = null,
    var position: Int? = null,
    var name: String? = null,
    val nsfw: Boolean? = null,
    @SerialName("parent_id")
    val parentId: Snowflake? = null,
    val type: ChannelType? = null,
    val recipients: List<ApiUser>? = null,
    val icon: Asset? = null,
    @SerialName("last_message_id")
    val lastMessageId: Snowflake? = null,
) {

    @Transient
    var children: MutableList<Snowflake> = mutableListOf()
}