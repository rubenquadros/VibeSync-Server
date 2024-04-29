package io.github.rubenquadros.vibesync.server.test.artist

import io.github.rubenquadros.vibesync.server.artist.ArtistApi
import io.github.rubenquadros.vibesync.server.artist.GetArtistResponse
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.test.data.albumsResponse
import io.github.rubenquadros.vibesync.test.data.artistResponse
import io.github.rubenquadros.vibesync.test.data.artistTopTracksResponse
import io.github.rubenquadros.vibesync.test.data.relatedArtistsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ArtistRouteTest : KoinTest {

    private val mockModule = module {
        single<ArtistApi> { FakeArtistApi() }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when artist data is fetched successfully then a success response is received`() = testApplication {
        FakeArtistApi.isError = false

        val response = it.get("artist/123")

        response.assertOk()

        val body = response.body<GetArtistResponse>()

        with(body) {
            assert(artistInfo.id == artistResponse.result.id)
            assert(albums.size == albumsResponse.result.items.size)
            assert(topTracks.size == artistTopTracksResponse.result.tracks.size)
            assert(relatedArtists.size == relatedArtistsResponse.result.artists.size)
        }
    }

    @Test
    fun `when there is error in fetching artist data from spotify then an error response is received`() = testApplication {
        FakeArtistApi.isError = true

        val response = it.get("artist/123")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}