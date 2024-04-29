package io.github.rubenquadros.vibesync.server

import io.github.rubenquadros.vibesync.server.album.albumRoute
import io.github.rubenquadros.vibesync.server.artist.artistRoute
import io.github.rubenquadros.vibesync.server.home.homeRoute
import io.github.rubenquadros.vibesync.server.playlist.playlistRoute
import io.github.rubenquadros.vibesync.server.search.searchRoute
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        homeRoute()
        artistRoute()
        albumRoute()
        playlistRoute()
        searchRoute()
    }
}
