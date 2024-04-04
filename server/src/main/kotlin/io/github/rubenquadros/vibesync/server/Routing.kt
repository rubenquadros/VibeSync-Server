package io.github.rubenquadros.vibesync.server

import io.github.rubenquadros.vibesync.server.home.homeRoute
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
       homeRoute()
    }
}
