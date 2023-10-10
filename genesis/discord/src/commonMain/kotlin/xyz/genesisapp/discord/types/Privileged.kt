package xyz.genesisapp.discord.types

sealed class PrivilegedValue<T>(val scope: String) {
    class NotPrivileged<T>(scope: String) : PrivilegedValue<T>(scope)
    class Privileged<T>(scope: String, val field: T) : PrivilegedValue<T>(scope)
    val value: T
        get() = when (this) {
            is NotPrivileged -> throw IllegalStateException("Cannot access value of NotPrivileged")
            is Privileged -> this.field
        }
    val isPrivileged: Boolean
        get() = this is Privileged

    companion object {
        fun <T> privileged(scope: String, field: T): PrivilegedValue<T> = Privileged(scope, field)
        fun <T> notPrivileged(scope: String): PrivilegedValue<T> = NotPrivileged(scope)
    }
}
