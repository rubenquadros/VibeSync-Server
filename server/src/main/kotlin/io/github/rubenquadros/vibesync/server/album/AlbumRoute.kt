package io.github.rubenquadros.vibesync.server.album

import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.albumRoute() {

    val albumApi by inject<AlbumApi>()

    get<Album> { album ->
        val response = albumApi.getAlbum(album.id)
        call.respond(status = response.status, message = response.data)
    }

    get<Album.Tracks> { tracks ->
        val response = albumApi.getAlbumTracks(tracks.parent.id, tracks.offset, tracks.limit)
        call.respond(status = response.status, message = response.data)
    }
}

@Resource("/album/{id}")
private data class Album(val id: String) {

    @Resource("/tracks")
    data class Tracks(val parent: Album, val offset: Int, val limit: Int)

}