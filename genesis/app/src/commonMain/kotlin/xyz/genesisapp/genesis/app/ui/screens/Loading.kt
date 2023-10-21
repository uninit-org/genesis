package xyz.genesisapp.genesis.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import xyz.genesisapp.common.preferences.PreferencesManager
import xyz.genesisapp.discord.api.domain.user.ApiClientUser
import xyz.genesisapp.discord.client.GenesisClient
import xyz.genesisapp.discord.client.api.ApiOptions

class LoadingScreen : Screen {

    @OptIn(DelicateCoroutinesApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val authNav = rememberScreen(RootSharedScreen.Auth)

        val koin = getKoin()
        val prefs = koin.get<PreferencesManager>()
        var token by remember { prefs.preference("auth.token", "") }
        val genesisClient = koin.get<GenesisClient>()

        Scaffold { pv ->
            Box(
                modifier = Modifier
                    .padding(pv)
                    .fillMaxSize()

            ) {

                Image(
                    painterResource("images/img_logo.png"),
                    contentDescription = "Genesis Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
        }

        GlobalScope.launch {
            delay(100)
            if (token.isNotEmpty()) {
                val response = genesisClient.api.get<ApiClientUser>(
                    "/users/@me", ApiOptions(
                        token = token
                    )
                )

                if (response.success) {
                    genesisClient.api.token = token
                    genesisClient.user.token = token
                    genesisClient.user = GenesisClient.ClientUser(
                        token = token,
                        email = response.data!!.email,
                        nsfwAllowed = response.data!!.nsfwAllowed ?: true
                    )
                    genesisClient.user.id = response.data!!.id
                    genesisClient.user.username = response.data!!.username
                    genesisClient.user.avatar = response.data!!.avatar
                    genesisClient.user.discriminator = response.data!!.discriminator
                    genesisClient.user.publicFlags = response.data!!.publicFlags
                    genesisClient.user.flags = response.data!!.flags
                    genesisClient.user.banner = response.data!!.banner
                    genesisClient.user.accentColor = response.data!!.accentColor
                    genesisClient.user.globalName = response.data!!.globalName
                    genesisClient.user.bannerColor = response.data!!.bannerColor
                    genesisClient.user.premiumType = response.data!!.premiumType
                    genesisClient.user.bio = response.data!!.bio


                    navigator.push(authNav) // go home
                } else {
                    token = ""
                    navigator.push(authNav)
                }
            } else {
                navigator.push(authNav)
            }
        }
    }
}