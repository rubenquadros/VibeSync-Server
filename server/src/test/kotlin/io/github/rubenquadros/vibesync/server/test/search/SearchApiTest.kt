package io.github.rubenquadros.vibesync.server.test.search

import io.github.rubenquadros.vibesync.server.model.GetPaginatedResponse
import io.github.rubenquadros.vibesync.server.model.TracksPage
import io.github.rubenquadros.vibesync.server.model.SearchAlbums
import io.github.rubenquadros.vibesync.server.model.SearchArtists
import io.github.rubenquadros.vibesync.server.model.SearchPlaylists
import io.github.rubenquadros.vibesync.server.search.SearchApiImpl
import io.github.rubenquadros.vibesync.server.test.FakeSpotifyApi
import io.github.rubenquadros.vibesync.server.test.assertSpotifyFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SearchApiTest {

    private val fakeSpotifyApi = FakeSpotifyApi()
    private val searchApi = SearchApiImpl(fakeSpotifyApi)

    @Test
    fun `when a track is searched successfully then a success response is received`() = runTest {
        fakeSpotifyApi.isError = false

        val response = searchApi.searchTrack(query = "abc", offset = 0, limit = 20)

        response.assertSuccess<GetPaginatedResponse> {
            with(it) {
                assert(isNext)
                assert((content as TracksPage).items.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in searching a track then an error response is received`() = runTest {
        fakeSpotifyApi.isError = true

        val response = searchApi.searchTrack(query = "abc", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }

    @Test
    fun `when an album is searched successfully then a success response is received`() = runTest {
        fakeSpotifyApi.isError = false

        val response = searchApi.searchAlbum(query = "abc", offset = 0, limit = 20)

        response.assertSuccess<GetPaginatedResponse> {
            with(it) {
                assert(isNext)
                assert((content as SearchAlbums).albums.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in searching an album then an error response is received`() = runTest {
        fakeSpotifyApi.isError = true

        val response = searchApi.searchAlbum(query = "abc", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }

    @Test
    fun `when an artist is searched successfully then a success response is received`() = runTest {
        fakeSpotifyApi.isError = false

        val response = searchApi.searchArtist(query = "abc", offset = 0, limit = 20)

        response.assertSuccess<GetPaginatedResponse> {
            with(it) {
                assert(isNext)
                assert((content as SearchArtists).artists.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in searching an artist then an error response is received`() = runTest {
        fakeSpotifyApi.isError = true

        val response = searchApi.searchArtist(query = "abc", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }

    @Test
    fun `when a playlist is searched successfully then a success response is received`() = runTest {
        fakeSpotifyApi.isError = false

        val response = searchApi.searchPlaylist(query = "abc", offset = 0, limit = 20)

        response.assertSuccess<GetPaginatedResponse> {
            with(it) {
                assert(isNext)
                assert((content as SearchPlaylists).playlists.isNotEmpty())
            }
        }
    }

    @Test
    fun `when there is an error in searching a playlist then an error response is received`() = runTest {
        fakeSpotifyApi.isError = true

        val response = searchApi.searchPlaylist(query = "abc", offset = 0, limit = 20)

        response.assertSpotifyFailure()
    }
}