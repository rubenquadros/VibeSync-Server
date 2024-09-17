package io.github.rubenquadros.vibesync.server.artist

import io.github.rubenquadros.kovibes.api.response.Albums
import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.kovibes.api.response.ArtistTopTracks
import io.github.rubenquadros.kovibes.api.response.RelatedArtists
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.kovibes.toMediaInfo
import io.github.rubenquadros.vibesync.kovibes.toTrackInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.util.toSpotifySuccessData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface ArtistApi {
    suspend fun getArtist(id: String): Response
}

@Single
internal class ArtistApiImpl(private val spotifyApi: SpotifyApi) : ArtistApi {

    override suspend fun getArtist(id: String): Response {
        val spotifyResponse = withContext(Dispatchers.IO) {
            listOf(
                async { spotifyApi.getArtist(id) },
                async { spotifyApi.getArtistAlbums(id) },
                async { spotifyApi.getArtistTopTracks(id) },
                async { spotifyApi.getRelatedArtists(id) }
            )
        }.awaitAll()

        return spotifyResponse.toSpotifySuccessData { successResults ->
            val artist = successResults[0] as Artist
            val artistAlbums = successResults[1] as Albums
            val artistTopTracks = successResults[2] as ArtistTopTracks
            val relatedArtists = successResults[3] as RelatedArtists

            getSuccessResponse(
                data = GetArtistResponse(
                    artistInfo = artist.toArtistInfo(),
                    albums = artistAlbums.items.map { it.toMediaInfo() },
                    topTracks = artistTopTracks.tracks.map { it.toTrackInfo() },
                    relatedArtists = relatedArtists.artists.map { it.toMediaInfo() }
                )
            )
        }
    }
}