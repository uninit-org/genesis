package uninit.genesis.app.di

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

actual val platformHttpEngineFactory: HttpClientEngineFactory<*> = Darwin