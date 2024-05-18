package io.github.rubenquadros.vibesync.server.test.artist

import io.github.rubenquadros.vibesync.kovibes.toMediaInfo
import io.github.rubenquadros.vibesync.kovibes.toTrackInfo
import io.github.rubenquadros.vibesync.server.artist.ArtistApi
import io.github.rubenquadros.vibesync.server.artist.GetArtistResponse
import io.github.rubenquadros.vibesync.server.artist.toArtistInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.test.apiErrorResponse
import io.github.rubenquadros.vibesync.test.data.albumsResponse
import io.github.rubenquadros.vibesync.test.data.artistResponse
import io.github.rubenquadros.vibesync.test.data.artistTopTracksResponse
import io.github.rubenquadros.vibesync.test.data.relatedArtistsResponse

class FakeArtistApi : ArtistApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getArtist(id: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getSuccessResponse(
                data = GetArtistResponse(
                    artistInfo = artistResponse.result.toArtistInfo(),
                    topTracks = artistTopTracksResponse.result.tracks.map { it.toTrackInfo() },
                    relatedArtists = relatedArtistsResponse.result.artists.map { it.toMediaInfo() },
                    albums = albumsResponse.result.items.map { it.toMediaInfo() }
                )
            )
        }
    }
}