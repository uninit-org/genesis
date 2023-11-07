package xyz.genesisapp.discord.api.types

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
}

fun Asset.toUrl(
    type: AssetType,
    parentId: Snowflake? = null,
    size: Int? = null
): String {
    var url = "https://cdn.discordapp.com/${type.value}/"
    if (parentId !== null) {
        url += "$parentId/"
    }
    url += extension
    if (size !== null) {
        url += "?size=$size"
    }
    return url
}