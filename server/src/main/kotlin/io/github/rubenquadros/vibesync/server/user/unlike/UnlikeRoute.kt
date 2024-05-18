package io.github.rubenquadros.vibesync.server.user.unlike

import io.github.rubenquadros.vibesync.server.user.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.resources.delete
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.unlikeRoute() {

    val unlikeApi by inject<UnlikeApi>()

    delete<User.Id.UnlikeTrack> { unlikeTrack ->
        val response = unlikeApi.unlikeTrack(unlikeTrack.parent.id, unlikeTrack.trackId)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    delete<User.Id.UnlikeAlbum> { unlikeAlbum ->
        val response = unlikeApi.unlikeAlbum(unlikeAlbum.parent.id, unlikeAlbum.albumId)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    delete<User.Id.UnlikePlaylist> { unlikePlaylist ->
        val response = unlikeApi.unlikePlaylist(unlikePlaylist.parent.id, unlikePlaylist.playlistId)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }
}