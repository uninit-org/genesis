package xyz.genesisapp.genesis.app.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import xyz.genesisapp.common.fytix.EventBus
import xyz.genesisapp.common.getTimeInMillis
import xyz.genesisapp.discord.api.types.Snowflake
import xyz.genesisapp.genesis.app.ui.screens.client.ClientTab

class DataStore() : EventBus("DataStore") {

    var mobileUi by mutableStateOf(false)
    val shiggyEasterEgg by mutableStateOf(getTimeInMillis() % 10 == 0L)


    /**
     * Explicit typing for [emit] function, event is [Snowflake]
     */
    fun selectGuild(guildId: Snowflake) = emit("GUILD_SELECT", guildId)

    /**
     * Explicit typing for [on] function, event is [Snowflake]
     */
    fun onGuildSelect(block: Event<Snowflake>.(Snowflake) -> Unit) =
        on("GUILD_SELECT", block)

    /**
     * Explicit typing for [compositionLocalOn] function, event is [Snowflake]
     */
    @Composable
    fun compositionOnGuildSelect(block: Event<Snowflake>.(Snowflake) -> Unit) =
        compositionLocalOn("GUILD_SELECT", block)

    /**
     * Explicit typing for [emit] function, event is [Snowflake]
     */
    fun selectChannel(channelId: Snowflake) = emit("CHANNEL_SELECT", channelId)

    /**
     * Explicit typing for [on] function, event is [Snowflake]
     */
    fun onChannelSelect(block: Event<Snowflake>.(Snowflake) -> Unit) =
        on("CHANNEL_SELECT", block)

    /**
     * Explicit typing for [compositionLocalOn] function, event is [Snowflake]
     */
    @Composable
    fun compositionOnChannelSelect(block: Event<Snowflake>.(Snowflake) -> Unit) =
        compositionLocalOn("CHANNEL_SELECT", block)

    /**
     * Explicit typing for [emit] function, event is [ClientTab]
     */
    fun selectClientTab(tab: ClientTab) = emit("CLIENT_TAB_SELECT", tab)

    /**
     * Explicit typing for [on] function, event is [ClientTab]
     */
    fun onClientTabSelect(block: Event<ClientTab>.(ClientTab) -> Unit) =
        on("CLIENT_TAB_SELECT", block)

    /**
     * Explicit typing for [compositionLocalOn] function, event is [ClientTab]
     */
    @Composable
    fun compositionOnClientTabSelect(block: Event<ClientTab>.(ClientTab) -> Unit) =
        compositionLocalOn("CLIENT_TAB_SELECT", block)

    /**
     * Explicit typing for [emit] function, event is [Boolean]
     */
    fun toggleGuilds() = emit("GUILDS_TOGGLE", true)

    /**
     * Explicit typing for [on] function, event is [Boolean]
     */
    fun onToggleGuilds(block: Event<Boolean>.(Boolean) -> Unit) =
        on("GUILDS_TOGGLE", block)

    /**
     * Explicit typing for [compositionLocalOn] function, event is [Boolean]
     */
    @Composable
    fun compositionOnToggleGuilds(block: Event<Boolean>.(Boolean) -> Unit) =
        compositionLocalOn("GUILDS_TOGGLE", block)
}