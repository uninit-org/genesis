package xyz.genesisapp.discord.client.entities

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import io.kamel.core.Resource
import io.kamel.core.isSuccess
import io.kamel.image.asyncPainterResource
import xyz.genesisapp.discord.api.types.Asset
import xyz.genesisapp.discord.api.types.AssetType
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.GenesisClient

class Asset(
    val genesisClient: GenesisClient,
    val hash: Asset,
    val type: AssetType,
    val parentId: Snowflake? = null,
) {
    private val sizeCache: MutableMap<Int, Resource<Painter>> = mutableMapOf()


    fun toUrl(extension: String = "png", size: Int? = null): String {
        val builder = StringBuilder()
            .append("https://cdn.discordapp.com/")
            .append(type.value)
            .append("/")
        parentId?.let {
            builder.append(it)
                .append("/")
        }
        builder.append(hash)
            .append(".")
            .append(extension)
        size?.let {
            builder.append("?size=")
                .append(it)
        }
        return builder.toString()
    }

    @Composable
    fun render(size: Int, extension: String = "png"): Resource<Painter> {
        if (!sizeCache.keys.contains(size) || !sizeCache[size]!!.isSuccess) {
            sizeCache[size] = asyncPainterResource(toUrl(extension, size))
        }
        return sizeCache[size]!!
    }
}