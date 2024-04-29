package io.github.rubenquadros.vibesync.server.test.home

import io.github.rubenquadros.vibesync.server.home.HomeApi
import io.github.rubenquadros.vibesync.server.home.HomeApiImpl
import io.github.rubenquadros.vibesync.server.home.LandingPageResponse
import io.github.rubenquadros.vibesync.server.test.FakeFirestoreApi
import io.github.rubenquadros.vibesync.server.test.FakeSpotifyApi
import io.github.rubenquadros.vibesync.server.test.assertFirestoreFailure
import io.github.rubenquadros.vibesync.server.test.assertSpotifyFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import io.github.rubenquadros.vibesync.test.data.featuredPlaylistsResponse
import io.github.rubenquadros.vibesync.test.data.topEntity
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
                assert(topAlbums.size == topEntity.size)
                assert(topTracks.size == topEntity.size)
                assert(topArtists.size == topEntity.size)
                assert(recentTracks?.size == topEntity.size)
                assert(featuredPlaylists.size == featuredPlaylistsResponse.result.items.size)
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
    fun `when there is an error in fetching firestore data then an error response is received`() = runTest {
        FakeFirestoreApi.isError = true
        FakeSpotifyApi.isError = false

        val response = homeApi.getHomePage()

        response.assertFirestoreFailure {
            assert(it.message == "Error in fetching top artists.")
        }
    }
}