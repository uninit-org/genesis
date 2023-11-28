package uninit.genesis.discord.api.types

import uninit.genesis.discord.api.domain.UtcDateTime

typealias Snowflake = Long

val Snowflake.timestamp: Long
    get() = (this shr 22) + UtcDateTime.DISCORD_EPOCH

val Snowflake.workerId: Int
    get() = ((this and 0x3E0000) shr 17).toInt()

val Snowflake.processId: Int
    get() = ((this and 0x1F000) shr 12).toInt()

val Snowflake.increment: Int
    get() = (this and 0xFFF).toInt()