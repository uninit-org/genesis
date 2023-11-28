package uninit.genesis.app.di

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual val platformHttpEngineFactory: HttpClientEngineFactory<*> = OkHttp