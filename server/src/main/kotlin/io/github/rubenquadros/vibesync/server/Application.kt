package io.github.rubenquadros.vibesync.server

import io.github.rubenquadros.vibesync.server.plugins.configureRouting
import io.github.rubenquadros.vibesync.server.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
