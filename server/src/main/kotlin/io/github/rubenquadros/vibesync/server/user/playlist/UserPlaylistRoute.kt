package io.github.rubenquadros.vibesync.server.user.playlist

import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.user.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.resources.patch
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Route.userPlaylistRoute() {

    val userPlaylistApi by inject<UserPlaylistApi>()

    post<User.Id.CreatePlaylist> { createPlaylist ->
        val body = call.receive<NewPlaylistInfo>()
        val response =
            userPlaylistApi.createPlaylist(createPlaylist.parent.id, body.owner, body.playlistName, body.track)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    get<User.Id.Playlists> { playlists ->
        val response = userPlaylistApi.getUserPlaylists(playlists.parent.id, playlists.offset)

        call.respond(status = response.status, message = response.data)
    }

    delete<User.Id.DeletePlaylist> { deletePlaylist ->
        val response = userPlaylistApi.deletePlaylist(deletePlaylist.parent.id, deletePlaylist.playlistId)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    patch<User.Id.UpdatePlaylist> { updatePlaylist ->
        val body = call.receive<TrackInfo>()
        val response = userPlaylistApi.addTrackToPlaylist(updatePlaylist.parent.id, updatePlaylist.playlistId, body)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    delete<User.Id.UpdatePlaylist> { updatePlaylist ->
        val body = call.receive<List<String>>()
        val response =
            userPlaylistApi.removeTracksFromPlaylist(updatePlaylist.parent.id, updatePlaylist.playlistId, body)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    get<User.Id.GetPlaylistTracks> { getPlaylistTracks ->
        val response = userPlaylistApi.getUserPlaylistTracks(
            getPlaylistTracks.parent.id,
            getPlaylistTracks.playlistId,
            getPlaylistTracks.offset
        )

        call.respond(status = response.status, message = response.data)
    }
}

@Serializable
data class NewPlaylistInfo(
    val playlistName: String,
    val owner: String,
    val track: TrackInfo
)