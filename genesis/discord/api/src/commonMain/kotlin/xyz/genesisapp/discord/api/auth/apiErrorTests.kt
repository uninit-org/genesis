package uninit.genesis.discord.api.auth

import uninit.genesis.discord.api.ApiError
import uninit.genesis.discord.api.ApiErrorPack


fun ApiErrorPack.isNewLocation(): Boolean = errors["login"]?.let {
    it.getOrNull(0)?.let {
        err -> err.code == "ACCOUNT_LOGIN_VERIFICATION_EMAIL"
    }
} ?: false

fun ApiErrorPack.isBadPassword(): Boolean = errors["login"]?.let {
    it.getOrNull(0)?.let {
        err -> err.code == "INVALID_LOGIN"
    }
} ?: false

fun ApiError.isBadMfa(): Boolean = code == 60008