package io.github.rubenquadros.vibesync.server.test.user.like

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.test.FakeApi
import io.github.rubenquadros.vibesync.server.user.like.LikeApi
import io.github.rubenquadros.vibesync.test.data.likedAlbums
import io.github.rubenquadros.vibesync.test.data.tracks

class FakeLikeApi : LikeApi, FakeApi() {

    override suspend fun likeTrack(userId: String, trackInfo: TrackInfo): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun getLikedTracks(userId: String): Response {
        return getTestApiResponse(tracks)
    }

    override suspend fun getLikedAlbums(userId: String): Response {
        return getTestApiResponse(likedAlbums)
    }
}