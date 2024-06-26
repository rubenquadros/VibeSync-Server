package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.vibesync.server.configureResources
import io.github.rubenquadros.vibesync.server.configureRouting
import io.github.rubenquadros.vibesync.server.configureSerialization
import io.github.rubenquadros.vibesync.test.startTestApplication
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module


fun testApplication(block: suspend (client: HttpClient) -> Unit) = startTestApplication {
    it.application {
        configureSerialization()
        configureResources()
        configureRouting()
    }
    val client = it.createClient {
        install(ContentNegotiation) {
            json(json = Json { ignoreUnknownKeys = true })
        }
    }
    block(client)
}

fun setupKoin(vararg modules: Module) {
    startKoin {  }
    loadKoinModules(modules.toList())
}

fun cleanupKoin(vararg modules: Module) {
    unloadKoinModules(modules.toList())
    stopKoin()
}