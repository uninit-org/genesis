package uninit.genesis.discord.api.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uninit.genesis.discord.api.domain.user.ApiUser
import uninit.genesis.discord.api.types.Snowflake

@Serializable
data class ApiMessageAttachment(
    @SerialName("content_type")
    var contentType: String,
    val filename: String,
    val height: Int? = null,
    val id: Snowflake,
    @SerialName("proxy_url")
    val proxyUrl: String,
    val url: String,
    val width: Int? = null,
)

@Serializable
data class ApiMessageEmbed(
    val id: Snowflake,
    val type: String,
    val image: ApiMessageEmbedImage? = null,
)

@Serializable
data class ApiMessageEmbedImage(
    val url: String,
    val proxyUrl: String? = null,
    val height: Int? = null,
    val width: Int,
)

@Serializable
data class DomainMessage(
    var id: Snowflake? = null,

    var content: String? = null,

    @SerialName("channel_id")
    var channelId: Snowflake? = null,

    val attachments: List<ApiMessageAttachment>? = null,
    val embeds: List<ApiMessageEmbed>? = null,

    var author: ApiUser? = null,
    val nonce: Long? = null,
) {
}