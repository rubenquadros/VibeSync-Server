package io.github.rubenquadros.vibesync.server.artist

import io.github.rubenquadros.kovibes.api.response.Albums
import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.kovibes.api.response.ArtistTopTracks
import io.github.rubenquadros.kovibes.api.response.RelatedArtists
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.github.rubenquadros.vibesync.server.model.toMediaInfo
import io.github.rubenquadros.vibesync.server.util.toApiResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface ArtistApi {
    suspend fun getArtist(id: String): Response
}

@Single
class ArtistApiImpl(private val spotifyApi: SpotifyApi) : ArtistApi {

    override suspend fun getArtist(id: String): Response {
        val spotifyResponse = runCatching {
            withContext(Dispatchers.IO) {
                listOf(
                    async { spotifyApi.getArtist(id) },
                    async { spotifyApi.getArtistAlbums(id) },
                    async { spotifyApi.getArtistTopTracks(id) },
                    async { spotifyApi.getRelatedArtists(id) }
                )
            }.awaitAll()
        }.getOrElse {
            return getErrorResponse(message = it.message.toString())
        }

        return spotifyResponse.toApiResponse { successResults ->
            val artist = successResults[0] as Artist
            val artistAlbums = successResults[1] as Albums
            val artistTopTracks = successResults[2] as ArtistTopTracks
            val relatedArtists = successResults[3] as RelatedArtists

            Response(
                status = HttpStatusCode.OK,
                data = GetArtistResponse(
                    artistInfo = artist.toArtistInfo(),
                    albums = artistAlbums.items.map { it.toMediaInfo() },
                    topTracks = artistTopTracks.tracks.map { it.toTopTrackInfo() },
                    relatedArtists = relatedArtists.artists.map { it.toMediaInfo() }
                )
            )
        }
    }
}