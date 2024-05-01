package io.github.rubenquadros.vibesync.server.test.playlist

import io.github.rubenquadros.vibesync.server.model.GetPaginatedResponse
import io.github.rubenquadros.vibesync.server.model.PlaylistTracks
import io.github.rubenquadros.vibesync.server.playlist.GetPlaylistResponse
import io.github.rubenquadros.vibesync.server.playlist.PlaylistApiImpl
import io.github.rubenquadros.vibesync.server.test.FakeSpotifyApi
import io.github.rubenquadros.vibesync.server.test.assertSpotifyFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import io.github.rubenquadros.vibesync.test.data.playlist1
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PlaylistApiTest {

    private val playlistApi = PlaylistApiImpl(FakeSpotifyApi())

    @Test
    fun `when playlist info is fetched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = playlistApi.getPlaylist(id = "123")

        response.assertSuccess<GetPlaylistResponse> {
            with(it) {
                assert(id == playlist1.id)
                assert(name == playlist1.name)
                assert(images.size == playlist1.images.size)
            }
        }
    }

    @Test
    fun `when there is an error in fetching spotify playlist info then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = playlistApi.getPlaylist("123")

        response.assertSpotifyFailure()
    }

    @Test
    fun `when playlist tracks are fetched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = playlistApi.getPlaylistTracks(id = "123", offset = 0, limit = 20)

        response.assertSuccess<GetPaginatedResponse> {
            with(it) {
                assert(!isNext)
                assert((content as PlaylistTracks).tracks.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in fetching spotify playlist tracks then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = playlistApi.getPlaylistTracks(id = "123", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }
}