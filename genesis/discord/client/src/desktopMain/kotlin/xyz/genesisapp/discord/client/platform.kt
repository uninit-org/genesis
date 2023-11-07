package xyz.genesisapp.discord.client

import xyz.genesisapp.discord.client.gateway.types.SuperProperties

actual fun getSuperProperties(): SuperProperties = SuperProperties(
    os = "Mac OS X",
    browser = "Chrome",
    device = "",
    system_locale = "en-US",
    browser_user_agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36",
    browser_version = "118.0.0.0",
    os_version = "10.15.7",
    release_channel = "canary",
    client_build_number = 244191,
    design_id = 0
)