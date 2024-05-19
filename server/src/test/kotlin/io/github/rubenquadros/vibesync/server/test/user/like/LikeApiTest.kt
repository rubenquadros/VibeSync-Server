package io.github.rubenquadros.vibesync.server.test.user.like

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.test.FakeFirestoreApi
import io.github.rubenquadros.vibesync.server.test.assertFirestoreFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import io.github.rubenquadros.vibesync.server.test.assertSuccessNoBody
import io.github.rubenquadros.vibesync.server.user.like.LikeApiImpl
import io.github.rubenquadros.vibesync.test.data.mediaInfo
import io.github.rubenquadros.vibesync.test.data.trackInfo
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LikeApiTest {

    private val fakeFirestoreApi = FakeFirestoreApi()
    private val likeApi = LikeApiImpl(fakeFirestoreApi)

    @Test
    fun `when a track is liked successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = likeApi.likeTrack("1234", trackInfo)

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in liking a track then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = likeApi.likeTrack("1234", trackInfo)

        response.assertFirestoreFailure()
    }

    @Test
    fun `when an album is liked successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = likeApi.likeAlbum("1234", mediaInfo)

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in liking an album then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = likeApi.likeAlbum("1234", mediaInfo)

        response.assertFirestoreFailure()
    }

    @Test
    fun `when a playlist is liked successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = likeApi.likePlaylist("1234", mediaInfo)

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in liking a playlist then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = likeApi.likePlaylist("1234", mediaInfo)

        response.assertFirestoreFailure()
    }

    @Test
    fun `when user liked tracks are fetched successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = likeApi.getLikedTracks("1234")

        response.assertSuccess<List<TrackInfo>> {
            assert(it.size == 1)
        }
    }

    @Test
    fun `when there is an error in fetching user liked tracks then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = likeApi.getLikedTracks("1234")

        response.assertFirestoreFailure()
    }

    @Test
    fun `when user liked albums are fetched successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = likeApi.getLikedAlbums("1234")

        response.assertSuccess<List<MediaInfo>> {
            assert(it.size == 1)
        }
    }

    @Test
    fun `when there is an error in fetching user liked albums then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = likeApi.getLikedAlbums("1234")

        response.assertFirestoreFailure()
    }
}