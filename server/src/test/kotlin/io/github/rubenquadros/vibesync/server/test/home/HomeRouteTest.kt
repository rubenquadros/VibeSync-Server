package io.github.rubenquadros.vibesync.server.test.home

import io.github.rubenquadros.vibesync.server.home.HomeApi
import io.github.rubenquadros.vibesync.server.home.LandingPageResponse
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.test.data.featuredPlaylistsResponse
import io.github.rubenquadros.vibesync.test.data.topEntity
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class HomeRouteTest: KoinTest {

    private val mockModule = module {
        single<HomeApi> { FakeHomeApi() }
    }
    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when data is fetched successfully then a success response is received`() = testApplication {
        FakeHomeApi.isError = false

        val response = it.get("/landing")

        response.assertOk()

        val body = response.body<LandingPageResponse>()

        with(body) {
            assert(topArtists.size == topEntity.size)
            assert(topTracks.size == topEntity.size)
            assert(recentTracks?.size == topEntity.size)
            assert(topAlbums.size == topEntity.size)
            assert(featuredPlaylists.size == featuredPlaylistsResponse.result.items.size)
        }
    }

    @Test
    fun `when there is an error in data fetching then an error response is received`() = testApplication {
        FakeHomeApi.isError = true

        val response = it.get("/landing")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}