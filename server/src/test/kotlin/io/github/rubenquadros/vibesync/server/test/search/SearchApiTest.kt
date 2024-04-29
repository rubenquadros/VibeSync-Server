package io.github.rubenquadros.vibesync.server.test.search

import io.github.rubenquadros.vibesync.server.search.GetSearchAlbumResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchArtistResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchPlaylistResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchTrackResponse
import io.github.rubenquadros.vibesync.server.search.SearchApiImpl
import io.github.rubenquadros.vibesync.server.test.FakeSpotifyApi
import io.github.rubenquadros.vibesync.server.test.assertSpotifyFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SearchApiTest {

    private val searchApi = SearchApiImpl(FakeSpotifyApi())

    @Test
    fun `when a track is searched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = searchApi.searchTrack(query = "abc", offset = 0, limit = 20)

        response.assertSuccess<GetSearchTrackResponse> {
            with(it) {
                assert(isNext)
                assert(tracks.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in searching a track then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = searchApi.searchTrack(query = "abc", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }

    @Test
    fun `when an album is searched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = searchApi.searchAlbum(query = "abc", offset = 0, limit = 20)

        response.assertSuccess<GetSearchAlbumResponse> {
            with(it) {
                assert(isNext)
                assert(albums.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in searching an album then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = searchApi.searchAlbum(query = "abc", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }

    @Test
    fun `when an artist is searched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = searchApi.searchArtist(query = "abc", offset = 0, limit = 20)

        response.assertSuccess<GetSearchArtistResponse> {
            with(it) {
                assert(isNext)
                assert(artists.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in searching an artist then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = searchApi.searchArtist(query = "abc", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }

    @Test
    fun `when a playlist is searched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = searchApi.searchPlaylist(query = "abc", offset = 0, limit = 20)

        response.assertSuccess<GetSearchPlaylistResponse> {
            with(it) {
                assert(isNext)
                assert(playlists.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in searching a playlist then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = searchApi.searchPlaylist(query = "abc", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }
}