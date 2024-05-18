package io.github.rubenquadros.vibesync.server.user.profile

import io.github.rubenquadros.vibesync.server.user.User
import io.ktor.server.application.call
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.profileRoute() {

    val profileApi by inject<ProfileApi>()

    get<User.Id> { profile ->
        val response = profileApi.getUserProfile(profile.id)
        call.respond(status = response.status, message = response.data!!)
    }
}