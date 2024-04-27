package io.github.rubenquadros.vibesync.server.test.album

import io.github.rubenquadros.vibesync.server.album.AlbumApiImpl
import io.github.rubenquadros.vibesync.server.album.GetAlbumResponse
import io.github.rubenquadros.vibesync.server.album.GetAlbumTracksResponse
import io.github.rubenquadros.vibesync.server.test.FakeSpotifyApi
import io.github.rubenquadros.vibesync.server.test.assertSpotifyFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import io.github.rubenquadros.vibesync.test.data.album1
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AlbumApiTest {

    private val albumApi = AlbumApiImpl(FakeSpotifyApi())

    @Test
    fun `when album info is fetched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = albumApi.getAlbum("123")

        response.assertSuccess<GetAlbumResponse> {
            assert(it.id == album1.id)
        }
    }

    @Test
    fun `when there is error in fetching spotify album data then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = albumApi.getAlbum("123")

        response.assertSpotifyFailure()
    }

    @Test
    fun `when album tracks are fetched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = albumApi.getAlbumTracks("123", 0, 20)

        response.assertSuccess<GetAlbumTracksResponse> {
            assert(it.tracks.isNotEmpty())
        }
    }

    @Test
    fun `when there is error in fetching spotify album tracks data then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = albumApi.getAlbumTracks("123", 0, 20)

        response.assertSpotifyFailure()
    }
}