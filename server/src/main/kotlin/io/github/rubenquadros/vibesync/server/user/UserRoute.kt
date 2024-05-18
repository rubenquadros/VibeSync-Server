package io.github.rubenquadros.vibesync.server.user

import io.github.rubenquadros.vibesync.server.user.like.likeRoute
import io.github.rubenquadros.vibesync.server.user.playlist.userPlaylistRoute
import io.github.rubenquadros.vibesync.server.user.profile.profileRoute
import io.github.rubenquadros.vibesync.server.user.unlike.unlikeRoute
import io.ktor.server.routing.Route

fun Route.userRoute() {
    profileRoute()
    likeRoute()
    unlikeRoute()
    userPlaylistRoute()
}