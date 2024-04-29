package io.github.rubenquadros.vibesync.server.test.search

import io.github.rubenquadros.vibesync.server.search.GetSearchAlbumResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchArtistResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchPlaylistResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchTrackResponse
import io.github.rubenquadros.vibesync.server.search.SearchApi
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SearchRouteTest : KoinTest {

    private val mockModule = module {
        single<SearchApi> { FakeSearchApi() }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when a track is searched successfully then a success response is received`() = testApplication {
        FakeSearchApi.isError = false

        val response = it.get("/search/track?query=abc&offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetSearchTrackResponse>()

        with(body) {
            assert(isNext)
            assert(tracks.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error is searching a track then an error response is received`() = testApplication {
        FakeSearchApi.isError = true

        val response = it.get("/search/track?query=abc&offset=0&limit=20")

        response.assertError()
    }

    @Test
    fun `when an album is searched successfully then a success response is received`() = testApplication {
        FakeSearchApi.isError = false

        val response = it.get("/search/album?query=abc&offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetSearchAlbumResponse>()

        with(body) {
            assert(isNext)
            assert(albums.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in searching an album then an error response is received`() = testApplication {
        FakeSearchApi.isError = true

        val response = it.get("/search/album?query=abc&offset=0&limit=20")

        response.assertError()
    }

    @Test
    fun `when an artist is searched successfully then a success response is received`() = testApplication {
        FakeSearchApi.isError = false

        val response = it.get("/search/artist?query=abc&offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetSearchArtistResponse>()

        with(body) {
            assert(isNext)
            assert(artists.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in searching an artist then an error response is received`() = testApplication {
        FakeSearchApi.isError = true

        val response = it.get("/search/artist?query=abc&offset=0&limit=20")

        response.assertError()
    }

    @Test
    fun `when a playlist is searched successfully then a success response is received`() = testApplication {
        FakeSearchApi.isError = false

        val response = it.get("/search/playlist?query=abc&offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetSearchPlaylistResponse>()

        with(body) {
            assert(isNext)
            assert(playlists.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in searching a playlist then an error response is received`() = testApplication {
        FakeSearchApi.isError = true

        val response = it.get("/search/playlist?query=abc&offset=0&limit=20")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}