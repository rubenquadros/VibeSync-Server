package io.github.rubenquadros.vibesync.server.test.user.playlist

import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getEmptyBodySuccessResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.test.apiErrorResponse
import io.github.rubenquadros.vibesync.server.user.playlist.UserPlaylistApi
import io.github.rubenquadros.vibesync.test.data.trackInfo
import io.github.rubenquadros.vibesync.test.data.userPlaylist

class FakeUserPlaylistApi : UserPlaylistApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun createPlaylist(
        userId: String,
        userName: String,
        playlistName: String,
        trackInfo: TrackInfo
    ): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun getUserPlaylists(userId: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getSuccessResponse(listOf(userPlaylist))
        }
    }

    override suspend fun deletePlaylist(userId: String, playlistId: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun addTrackToPlaylist(userId: String, playlistId: String, trackInfo: TrackInfo): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun removeTracksFromPlaylist(
        userId: String,
        playlistId: String,
        trackIds: List<String>
    ): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun getUserPlaylistTracks(userId: String, playlistId: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getSuccessResponse(listOf(trackInfo))
        }
    }
}