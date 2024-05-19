package io.github.rubenquadros.vibesync.server.test.artist

import io.github.rubenquadros.vibesync.kovibes.toMediaInfo
import io.github.rubenquadros.vibesync.kovibes.toTrackInfo
import io.github.rubenquadros.vibesync.server.artist.ArtistApi
import io.github.rubenquadros.vibesync.server.artist.GetArtistResponse
import io.github.rubenquadros.vibesync.server.artist.toArtistInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.test.FakeApi
import io.github.rubenquadros.vibesync.test.data.albumsResponse
import io.github.rubenquadros.vibesync.test.data.artist1
import io.github.rubenquadros.vibesync.test.data.artistTopTracksResponse
import io.github.rubenquadros.vibesync.test.data.relatedArtistsResponse

class FakeArtistApi : ArtistApi, FakeApi() {

    override suspend fun getArtist(id: String): Response {
        return getTestApiResponse(
            GetArtistResponse(
                artistInfo = artist1.toArtistInfo(),
                topTracks = artistTopTracksResponse.tracks.map { it.toTrackInfo() },
                relatedArtists = relatedArtistsResponse.artists.map { it.toMediaInfo() },
                albums = albumsResponse.items.map { it.toMediaInfo() }
            )
        )
    }
}