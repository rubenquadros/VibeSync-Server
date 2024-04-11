package io.github.rubenquadros.vibesync.server.test.home

import io.github.rubenquadros.vibesync.server.home.HomeApi
import io.github.rubenquadros.vibesync.server.home.LandingPageResponse
import io.github.rubenquadros.vibesync.server.model.Error
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
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
        startKoin {  }
        loadKoinModules(mockModule)
    }

    @Test
    fun `when data is fetched successfully then a success response is received`() {
        testApplication {
            val response = it.get("/landing")
            val body = response.body<LandingPageResponse>()
            assert(response.status == HttpStatusCode.OK)
            assert(body.topArtists.size == 2)
            assert(body.topTracks.size == 2)
            assert(body.recentTracks?.size == 2)
            assert(body.topAlbums.size == 2)
            assert(body.featuredPlaylists.size == 2)
        }
    }

    @Test
    fun `when there is an error in data fetching then an error response is received`() {
        testApplication {
            FakeHomeApi.isError = true
            val response = it.get("/landing")
            val body = response.body<Error>()
            assert(response.status == HttpStatusCode.InternalServerError)
            assert(body.message == "Something went wrong.")
        }
    }

    @AfterTest
    fun cleanup() {
        unloadKoinModules(mockModule)
        stopKoin()
    }
}