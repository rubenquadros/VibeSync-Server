package io.github.rubenquadros.vibesync.server.test.artist

import io.github.rubenquadros.vibesync.server.artist.ArtistApi
import io.github.rubenquadros.vibesync.server.artist.GetArtistResponse
import io.github.rubenquadros.vibesync.server.artist.toArtistInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getServerErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.model.toMediaInfo
import io.github.rubenquadros.vibesync.server.model.toTrackInfo
import io.github.rubenquadros.vibesync.server.test.errorMessage
import io.github.rubenquadros.vibesync.test.data.albums
import io.github.rubenquadros.vibesync.test.data.artist
import io.github.rubenquadros.vibesync.test.data.artistTopTracks
import io.github.rubenquadros.vibesync.test.data.relatedArtists

class FakeArtistApi : ArtistApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getArtist(id: String): Response {
        return if (isError) {
            getServerErrorResponse(errorMessage)
        } else {
            getSuccessResponse(
                data = GetArtistResponse(
                    artistInfo = artist.result.toArtistInfo(),
                    topTracks = artistTopTracks.result.tracks.map { it.toTrackInfo() },
                    relatedArtists = relatedArtists.result.artists.map { it.toMediaInfo() },
                    albums = albums.result.items.map { it.toMediaInfo() }
                )
            )
        }
    }
}