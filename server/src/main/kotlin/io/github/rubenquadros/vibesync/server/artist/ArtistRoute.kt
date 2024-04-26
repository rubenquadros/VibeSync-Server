package io.github.rubenquadros.vibesync.server.artist

import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.artistRoute() {

    val artistApi by inject<ArtistApi>()

    get<Artist> { artist ->
        val response = artistApi.getArtist(artist.id)
        call.respond(status = response.status, message = response.data)
    }
}

@Resource("/artist/{id}")
data class Artist(val id: String)