package xyz.genesisapp.discord.api.types

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This API is experimental. It's currently in development on discord's side and may change at any time."
)
annotation class ExperimentalDiscordApi()
