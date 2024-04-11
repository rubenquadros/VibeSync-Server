package io.github.rubenquadros.vibesync.server.test.home

import io.github.rubenquadros.vibesync.server.home.HomeApi
import io.github.rubenquadros.vibesync.server.home.HomeApiImpl
import io.github.rubenquadros.vibesync.server.home.LandingPageResponse
import io.github.rubenquadros.vibesync.server.test.FakeFirestoreApi
import io.github.rubenquadros.vibesync.server.test.FakeSpotifyApi
import io.github.rubenquadros.vibesync.server.test.assertFirestoreFailure
import io.github.rubenquadros.vibesync.server.test.assertSpotifyFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HomeApiTest {

    private val homeApi: HomeApi = HomeApiImpl(
        firestoreApi = FakeFirestoreApi(),
        spotifyApi = FakeSpotifyApi()
    )

    @Test
    fun `when home page data is fetched successfully then a success response is received`() = runTest {
        FakeFirestoreApi.isError = false
        FakeSpotifyApi.isError = false
        val response = homeApi.getHomePage()
        response.assertSuccess<LandingPageResponse> {
            with(it) {
                assert(topAlbums.size == 2)
                assert(topTracks.size == 2)
                assert(topArtists.size == 2)
                assert(recentTracks?.size == 2)
                assert(featuredPlaylists.size == 2)
            }
        }
    }

    @Test
    fun `when there is an error in fetching spotify data then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true
        FakeFirestoreApi.isError = false
        val response = homeApi.getHomePage()
        response.assertSpotifyFailure()
    }

    @Test
    fun `when there is n error in fetching firestore data then an error response is received`() = runTest {
        FakeFirestoreApi.isError = true
        FakeSpotifyApi.isError = false
        val response = homeApi.getHomePage()
        response.assertFirestoreFailure {
            assert(it.message == "Error in fetching top artists.")
        }
    }
}