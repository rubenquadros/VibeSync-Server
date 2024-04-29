package io.github.rubenquadros.vibesync.server.playlist

import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.playlistRoute() {

    val playlistApi by inject<PlaylistApi>()

    get<Playlist> { playlist ->
        val response = playlistApi.getPlaylist(playlist.id)
        call.respond(status = response.status, message = response.data)
    }

    get<Playlist.Tracks> { tracks ->
        val response = playlistApi.getPlaylistTracks(tracks.parent.id, tracks.offset, tracks.limit)
        call.respond(status = response.status, message = response.data)
    }
}

@Resource("/playlist/{id}")
private data class Playlist(val id: String) {

    @Resource("tracks")
    data class Tracks(val parent: Playlist, val offset: Int, val limit: Int)
}