package io.github.rubenquadros.vibesync.server.test.user.like

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getEmptyBodySuccessResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.test.apiErrorResponse
import io.github.rubenquadros.vibesync.server.user.like.LikeApi
import io.github.rubenquadros.vibesync.test.data.mediaInfo
import io.github.rubenquadros.vibesync.test.data.trackInfo

class FakeLikeApi : LikeApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun likeTrack(userId: String, trackInfo: TrackInfo): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun getLikedTracks(userId: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getSuccessResponse(listOf(trackInfo))
        }
    }

    override suspend fun getLikedAlbums(userId: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getSuccessResponse(listOf(mediaInfo))
        }
    }
}