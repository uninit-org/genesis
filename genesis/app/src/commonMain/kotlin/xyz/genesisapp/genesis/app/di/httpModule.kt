package uninit.genesis.app.di

import io.ktor.client.engine.*
import org.koin.dsl.module


fun httpModule() = module {
    single { platformHttpEngineFactory }
}

expect val platformHttpEngineFactory: HttpClientEngineFactory<*>