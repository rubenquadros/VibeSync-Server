package io.github.rubenquadros.vibesync.server.user.like

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.user.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.likeRoute() {

    val likeApi by inject<LikeApi>()

    post<User.Id.LikeTrack> { likeTrack ->
        val body = call.receive<TrackInfo>()
        val response = likeApi.likeTrack(likeTrack.parent.id, body)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    post<User.Id.LikeAlbum> { likeAlbum ->
        val body = call.receive<MediaInfo>()
        val response = likeApi.likeAlbum(likeAlbum.parent.id, body)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    post<User.Id.LikePlaylist> { likePlaylist ->
        val body = call.receive<MediaInfo>()
        val response = likeApi.likePlaylist(likePlaylist.parent.id, body)

        if (response.status == HttpStatusCode.OK) {
            call.respond(response.status)
        } else {
            call.respond(status = response.status, message = response.data)
        }
    }

    get<User.Id.LikedTracks> { likedTracks ->
        val response = likeApi.getLikedTracks(likedTracks.parent.id, likedTracks.offset)

        call.respond(status = response.status, message = response.data)
    }

    get<User.Id.LikedAlbums> { likedAlbums ->
        val response = likeApi.getLikedAlbums(likedAlbums.parent.id, likedAlbums.offset)

        call.respond(status = response.status, message = response.data)
    }
}