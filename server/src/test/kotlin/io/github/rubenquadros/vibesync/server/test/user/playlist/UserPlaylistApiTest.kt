package io.github.rubenquadros.vibesync.server.test.user.playlist

import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.server.test.FakeFirestoreApi
import io.github.rubenquadros.vibesync.server.test.assertFirestoreFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import io.github.rubenquadros.vibesync.server.test.assertSuccessNoBody
import io.github.rubenquadros.vibesync.server.user.playlist.UserPlaylistApiImpl
import io.github.rubenquadros.vibesync.test.data.trackInfo
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class UserPlaylistApiTest {

    private val fakeFirestoreApi = FakeFirestoreApi()
    private val userPlaylistApi = UserPlaylistApiImpl(fakeFirestoreApi)

    @Test
    fun `when a playlist is created successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = userPlaylistApi.createPlaylist("1234", "TestUser", "TestPlaylist", trackInfo)

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in creating a playlist then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = userPlaylistApi.createPlaylist("1234", "TestUser", "TestPlaylist", trackInfo)

        response.assertFirestoreFailure()
    }

    @Test
    fun `when the user playlists are retrieved successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = userPlaylistApi.getUserPlaylists("1234")

        response.assertSuccess<List<PlaylistInfo>> {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in retrieving the user playlists then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = userPlaylistApi.getUserPlaylists("1234")

        response.assertFirestoreFailure()
    }

    @Test
    fun `when a playlist is deleted successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = userPlaylistApi.deletePlaylist("1234", "6789")

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in deleting a playlist then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = userPlaylistApi.deletePlaylist("1234", "6789")

        response.assertFirestoreFailure()
    }

    @Test
    fun `when a new track is added successfully to a playlist then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = userPlaylistApi.addTrackToPlaylist("1234", "6789", trackInfo)

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in adding a track to a playlist then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = userPlaylistApi.addTrackToPlaylist("1234", "6789", trackInfo)

        response.assertFirestoreFailure()
    }

    @Test
    fun `when a track is removed successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = userPlaylistApi.removeTracksFromPlaylist("1234", "6780", listOf("4567"))

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in removing a track from a playlist then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = userPlaylistApi.removeTracksFromPlaylist("1234", "6780", listOf("4567"))

        response.assertFirestoreFailure()
    }

    @Test
    fun `when user playlist tracks are retrieved successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = userPlaylistApi.getUserPlaylistTracks("1234", "6789")

        response.assertSuccess<List<TrackInfo>> {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in fetching user playlist tracks then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = userPlaylistApi.getUserPlaylistTracks("1234", "6789")

        response.assertFirestoreFailure()
    }
}