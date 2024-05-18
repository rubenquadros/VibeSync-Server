package io.github.rubenquadros.vibesync.server.test.user.unlike

import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getEmptyBodySuccessResponse
import io.github.rubenquadros.vibesync.server.test.apiErrorResponse
import io.github.rubenquadros.vibesync.server.user.unlike.UnlikeApi

class FakeUnlikeApi : UnlikeApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun unlikeTrack(userId: String, trackId: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun unlikeAlbum(userId: String, albumId: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun unlikePlaylist(userId: String, playlistId: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }
}