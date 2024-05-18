package io.github.rubenquadros.vibesync.server.test.user.unlike

import io.github.rubenquadros.vibesync.server.test.FakeFirestoreApi
import io.github.rubenquadros.vibesync.server.test.assertFirestoreFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccessNoBody
import io.github.rubenquadros.vibesync.server.user.unlike.UnlikeApiImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class UnlikeApiTest {

    private val unlikeApi = UnlikeApiImpl(FakeFirestoreApi())

    @Test
    fun `when a track is unliked successfully then a success response is received`() = runTest {
        FakeFirestoreApi.isError = false

        val response = unlikeApi.unlikeTrack("1234", "5678")

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in unliking a track then an error response is received`() = runTest {
        FakeFirestoreApi.isError = true

        val response = unlikeApi.unlikeTrack("1234", "5678")

        response.assertFirestoreFailure()
    }

    @Test
    fun `when an album is unliked successfully then a success response is received`() = runTest {
        FakeFirestoreApi.isError = false

        val response = unlikeApi.unlikeAlbum("1234", "5678")

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in unliking an album then an error response is received`() = runTest {
        FakeFirestoreApi.isError = true

        val response = unlikeApi.unlikeAlbum("1234", "5678")

        response.assertFirestoreFailure()
    }

    @Test
    fun `when a playlist is unliked successfully then a success response is received`() = runTest {
        FakeFirestoreApi.isError = false

        val response = unlikeApi.unlikePlaylist("1234", "5678")

        response.assertSuccessNoBody()
    }

    @Test
    fun `when there is an error in unliking a playlist then an error response is received`() = runTest {
        FakeFirestoreApi.isError = true

        val response = unlikeApi.unlikePlaylist("1234", "5678")

        response.assertFirestoreFailure()
    }
}