package io.github.rubenquadros.vibesync.server.test.playlist

import io.github.rubenquadros.vibesync.server.playlist.GetPlaylistResponse
import io.github.rubenquadros.vibesync.server.playlist.GetPlaylistTrackResponse
import io.github.rubenquadros.vibesync.server.playlist.PlaylistApi
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.test.data.playlist1
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class PlaylistRouteTest : KoinTest {

    private val mockModule = module {
        single<PlaylistApi> { FakePlaylistApi() }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when playlist data is fetched successfully then a success response is received`() = testApplication {
        FakePlaylistApi.isError = false

        val response = it.get("/playlist/12")

        response.assertOk()

        val body = response.body<GetPlaylistResponse>()

        with(body) {
            assert(id == playlist1.id)
            assert(name == playlist1.name)
            assert(images.size == playlist1.images.size)
        }
    }

    @Test
    fun `when there is an error in fetching playlist data from spotify then an error response is received`() = testApplication {
        FakePlaylistApi.isError = true

        val response = it.get("/playlist/12")

        response.assertError()
    }

    @Test
    fun `when playlist tracks data is fetched successfully then a success response is received`() = testApplication {
        FakePlaylistApi.isError = false

        val response = it.get("/playlist/12/tracks?offset=0&limit=20")

        response.assertOk()

        val body = response.body<GetPlaylistTrackResponse>()

        with(body) {
            assert(!isNext)
            assert(tracks.isNotEmpty())
        }
    }

    @Test
    fun `when there is an error in fetching playlist tracks data from spotify then an error response is received`() = testApplication {
        FakePlaylistApi.isError = true

        val response = it.get("/playlist/12/tracks?offset=0&limit=20")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}