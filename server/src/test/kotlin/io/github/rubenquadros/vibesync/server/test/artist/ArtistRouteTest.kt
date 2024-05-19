package io.github.rubenquadros.vibesync.server.test.artist

import io.github.rubenquadros.vibesync.server.artist.ArtistApi
import io.github.rubenquadros.vibesync.server.artist.GetArtistResponse
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.test.data.albumsResponse
import io.github.rubenquadros.vibesync.test.data.artist1
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

    private val fakeArtistApi = FakeArtistApi()
    private val mockModule = module {
        single<ArtistApi> { fakeArtistApi }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when artist data is fetched successfully then a success response is received`() = testApplication {
        fakeArtistApi.isError = false

        val response = it.get("artist/123")

        response.assertOk()

        val body = response.body<GetArtistResponse>()

        with(body) {
            assert(artistInfo.id == artist1.id)
            assert(albums.size == albumsResponse.items.size)
            assert(topTracks.size == artistTopTracksResponse.tracks.size)
            assert(relatedArtists.size == relatedArtistsResponse.artists.size)
        }
    }

    @Test
    fun `when there is error in fetching artist data from spotify then an error response is received`() = testApplication {
        fakeArtistApi.isError = true

        val response = it.get("artist/123")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}