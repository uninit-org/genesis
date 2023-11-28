package uninit.genesis.discord.entities.guild

import kotlinx.serialization.Serializable
import uninit.genesis.discord.api.types.Snowflake

@Serializable
data class ApiRole(
    val id: Snowflake,
    var name: String,
    var color: Int,
    var hoist: Boolean,
    var icon: String? = null,
) {

}