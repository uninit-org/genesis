package xyz.genesisapp.discord.api.annotations

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This implementation of the Discord API might not be correct, using a bad implementation may result in account termination."
)

annotation class UnconfirmedDiscordApi()