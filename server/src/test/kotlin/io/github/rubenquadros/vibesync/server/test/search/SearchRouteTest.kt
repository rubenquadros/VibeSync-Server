package io.github.rubenquadros.vibesync.server.test.search

import io.github.rubenquadros.vibesync.server.model.GetPaginatedResponse
import io.github.rubenquadros.vibesync.server.model.SearchAlbums
import io.github.rubenquadros.vibesync.server.model.SearchArtists
import io.github.rubenquadros.vibesync.server.model.SearchPlaylists
import io.github.rubenquadros.vibesync.server.model.SearchTracks
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

    private val fakeSearchApi = FakeSearchApi()
    private val mockModule = module {
        single<SearchApi> { fakeSearchApi }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when a track is searched successfully then a success response is received`() = testApplication {
        fakeSearchApi.isError = false

        val response = it.get("/search/track?query=abc&offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetPaginatedResponse>()

        with(body) {
            assert(isNext)
            assert((content as SearchTracks).tracks.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error is searching a track then an error response is received`() = testApplication {
        fakeSearchApi.isError = true

        val response = it.get("/search/track?query=abc&offset=0&limit=20")

        response.assertError()
    }

    @Test
    fun `when an album is searched successfully then a success response is received`() = testApplication {
        fakeSearchApi.isError = false

        val response = it.get("/search/album?query=abc&offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetPaginatedResponse>()

        with(body) {
            assert(isNext)
            assert((content as SearchAlbums).albums.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in searching an album then an error response is received`() = testApplication {
        fakeSearchApi.isError = true

        val response = it.get("/search/album?query=abc&offset=0&limit=20")

        response.assertError()
    }

    @Test
    fun `when an artist is searched successfully then a success response is received`() = testApplication {
        fakeSearchApi.isError = false

        val response = it.get("/search/artist?query=abc&offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetPaginatedResponse>()

        with(body) {
            assert(isNext)
            assert((content as SearchArtists).artists.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in searching an artist then an error response is received`() = testApplication {
        fakeSearchApi.isError = true

        val response = it.get("/search/artist?query=abc&offset=0&limit=20")

        response.assertError()
    }

    @Test
    fun `when a playlist is searched successfully then a success response is received`() = testApplication {
        fakeSearchApi.isError = false

        val response = it.get("/search/playlist?query=abc&offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetPaginatedResponse>()

        with(body) {
            assert(isNext)
            assert((content as SearchPlaylists).playlists.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in searching a playlist then an error response is received`() = testApplication {
        fakeSearchApi.isError = true

        val response = it.get("/search/playlist?query=abc&offset=0&limit=20")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}