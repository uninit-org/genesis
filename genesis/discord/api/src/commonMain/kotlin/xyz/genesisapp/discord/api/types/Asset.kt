package uninit.genesis.discord.api.types

typealias Asset = String

val Asset.isAnimated: Boolean
    get() = this.startsWith("a_")

val Asset.extension: String
    get() {
        val animated = isAnimated
        val normalized = if (animated) this.substring(2) else this
        return "$normalized.${if (animated) "gif" else "png"}"
    }

enum class AssetType(val value: String) {
    Avatar("avatars"),
    Banner("banners"),
    Icon("icons"),
    ChannelIcon("channel-icons"),
}

fun Asset.toUrl(
    type: AssetType,
    parentId: Snowflake? = null,
    size: Int? = null
): String {
    val builder = StringBuilder()
        .append("https://cdn.discordapp.com/")
        .append(type.value)
        .append("/")
    parentId?.let {
        builder.append(it)
            .append("/")
    }
    builder.append(".")
        .append(extension)
    size?.let {
        builder.append("?size=")
            .append(it)
    }
    return builder.toString()
}