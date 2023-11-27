package xyz.genesisapp.discord.client

import androidx.compose.runtime.mutableStateMapOf
import io.ktor.client.engine.*
import xyz.genesisapp.common.fytix.Err
import xyz.genesisapp.common.fytix.EventBus
import xyz.genesisapp.common.fytix.Ok
import xyz.genesisapp.common.fytix.Result
import xyz.genesisapp.discord.api.ApiError
import xyz.genesisapp.discord.api.annotations.ExperimentalDiscordApi
import xyz.genesisapp.discord.api.domain.DomainMessage
import xyz.genesisapp.discord.api.domain.user.DomainMe
import xyz.genesisapp.discord.api.domain.user.UserSettings
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.discord.client.entities.guild.Channel
import xyz.genesisapp.discord.client.entities.guild.Guild
import xyz.genesisapp.discord.client.entities.guild.User
import xyz.genesisapp.discord.client.enum.LogLevel
import xyz.genesisapp.discord.client.gateway.GatewayClient
import xyz.genesisapp.discord.client.gateway.types.events.Ready
import xyz.genesisapp.discord.client.rest.RestClient
import xyz.genesisapp.discord.entities.guild.GuildMember

@OptIn(ExperimentalDiscordApi::class)
class GenesisClient(
    httpClientEngineFactory: HttpClientEngineFactory<*>,
) : EventBus("GenesisClient") {
    lateinit var user: DomainMe // ClientUser
    lateinit var normalUser: User
    internal var loggedIn: Boolean = false

    var logLevel: LogLevel = LogLevel.INFO
    val rest: RestClient = RestClient(httpClientEngineFactory, genesisClient = this)
    val gateway: GatewayClient = GatewayClient(httpClientEngineFactory, parentClient = this)

    var guilds = mutableStateMapOf<Snowflake, Guild?>()
    var channels = mutableStateMapOf<Snowflake, Channel>()
    var guildMembers = mutableStateMapOf<Snowflake, GuildMember>()
    var userSettings: UserSettings? = null

    suspend fun tryTokenLogin(token: String): Result<DomainMe, ApiError> {
        rest.token = token
        return when (val response = rest.getDomainMe()) {
            is Ok -> {
                user = response.value
                loggedIn = true
                Ok(response.value)
            }

            is Err -> {
                rest.token = "" // Clear token
                Err(response.error)
            }
        }

    }

    /**
     * Explicit typing for [on] function, event is [Ready]
     */
    fun onReady(block: Event<Ready>.(Ready) -> Unit) = on("READY", block)

    /**
     * Explicit typing for [once] function, event is [Ready]
     */
    fun onceReady(block: Event<Ready>.(Ready) -> Unit) = once("READY", block)

    /**
     * Explicit typing for [waitFor] function, event is [Ready]
     */
    suspend fun waitForReady() = waitFor<Ready>("READY")

    /**
     * Explicit typing for [on] function, event is [DomainMessage]
     */
    fun onMessageCreate(block: Event<DomainMessage>.(DomainMessage) -> Unit) =
        on("MESSAGE_CREATE", block)

    /**
     * Explicit typing for [once] function, event is [DomainMessage]
     */
    fun onceMessageCreate(block: Event<DomainMessage>.(DomainMessage) -> Unit) =
        once("MESSAGE_CREATE", block)

    init {
        onMessageCreate {

        }
    }

}