package io.github.rubenquadros.vibesync.server.test.album

import io.github.rubenquadros.vibesync.server.album.AlbumApi
import io.github.rubenquadros.vibesync.server.album.GetAlbumResponse
import io.github.rubenquadros.vibesync.server.album.GetAlbumTracksResponse
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.test.data.album1
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class AlbumRouteTest : KoinTest {

    private val mockModule = module {
        single<AlbumApi> { FakeAlbumApi() }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when album data is fetched successfully then a success response is received`() = testApplication {
        FakeAlbumApi.isError = false

        val response = it.get("album/12")

        response.assertOk()

        val body = response.body<GetAlbumResponse>()

        assert(body.id == album1.id)
    }

    @Test
    fun `when there is an error in fetching album data from spotify then an error response is received`() = testApplication {
        FakeAlbumApi.isError = true

        val response = it.get("album/12")

        response.assertError()
    }

    @Test
    fun `when album tracks data is fetched successfully then a success response is received`() = testApplication {
        FakeAlbumApi.isError = false

        val response = it.get("album/12/tracks?offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetAlbumTracksResponse>()

        assert(body.tracks.isNotEmpty())
    }

    @Test
    fun `when there is an error in fetching album tracks data from spotify then an error response is received`() = testApplication {
        FakeAlbumApi.isError = true

        val response = it.get("album/12/tracks?offset=0&limit=20")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}