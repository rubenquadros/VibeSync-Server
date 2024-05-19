package io.github.rubenquadros.vibesync.server.test.user.unlike

import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.test.FakeApi
import io.github.rubenquadros.vibesync.server.user.unlike.UnlikeApi

class FakeUnlikeApi : UnlikeApi, FakeApi() {

    override suspend fun unlikeTrack(userId: String, trackId: String): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun unlikeAlbum(userId: String, albumId: String): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun unlikePlaylist(userId: String, playlistId: String): Response {
        return getEmptyBodyTestApiResponse()
    }
}