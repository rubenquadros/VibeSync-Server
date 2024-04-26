package io.github.rubenquadros.vibesync.server.test.artist

import io.github.rubenquadros.vibesync.server.artist.ArtistApiImpl
import io.github.rubenquadros.vibesync.server.artist.GetArtistResponse
import io.github.rubenquadros.vibesync.server.test.FakeSpotifyApi
import io.github.rubenquadros.vibesync.server.test.assertSpotifyFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import io.github.rubenquadros.vibesync.test.data.artist
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ArtistApiTest {

    private val artistApi = ArtistApiImpl(FakeSpotifyApi())

    @Test
    fun `when artist info is fetched successfully then a success response is received`() = runTest {
        FakeSpotifyApi.isError = false

        val response = artistApi.getArtist("123")

        response.assertSuccess<GetArtistResponse> {
            with(it) {
                assert(artistInfo.id == artist.result.id)
                assert(albums.size == 2)
                assert(relatedArtists.size == 1)
                assert(topTracks.size == 2)
            }
        }
    }

    @Test
    fun `when there is error in fetching spotify data then an error response is received`() = runTest {
        FakeSpotifyApi.isError = true

        val response = artistApi.getArtist("123")

        response.assertSpotifyFailure()
    }
}