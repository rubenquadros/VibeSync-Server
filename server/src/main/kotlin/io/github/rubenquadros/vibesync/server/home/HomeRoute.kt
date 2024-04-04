package io.github.rubenquadros.vibesync.server.home

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject

fun Route.homeRoute() {

    val homeApi by inject<HomeApi>()

    get("/landing") {
        val response = homeApi.getHomePage()
        call.respond(status = response.status, message = response.data)
    }
}