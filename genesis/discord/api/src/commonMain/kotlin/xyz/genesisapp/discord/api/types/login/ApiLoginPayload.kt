package xyz.genesisapp.discord.api.types.login

import kotlinx.serialization.Serializable

@Serializable
data class ApiLoginPayload(
    val gift_code_sku_id: String? = null,
    val login: String,
    val login_source: String? = null,
    val password: String,
    val undelete: Boolean = false
)