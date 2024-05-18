package io.github.rubenquadros.vibesync.server.album

import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.kovibes.toImage
import io.github.rubenquadros.vibesync.kovibes.toTrackInfo
import io.github.rubenquadros.vibesync.server.artist.toArtistInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface AlbumApi {
    suspend fun getAlbum(id: String): Response

    suspend fun getAlbumTracks(id: String, offset: Int, limit: Int): Response
}

@Single
class AlbumApiImpl(private val spotifyApi: SpotifyApi) : AlbumApi {
    override suspend fun getAlbum(id: String): Response {
        val spotifyResponse = withContext(Dispatchers.IO) {
            spotifyApi.getAlbum(id)
        }

        return if (spotifyResponse is SpotifyApiResponse.Success) {
            with(spotifyResponse.result) {
                getSuccessResponse(
                    data = GetAlbumResponse(
                        id = this.id,
                        name = name,
                        albumType = albumType,
                        releaseDate = releaseDate,
                        artists = artists.map { it.toArtistInfo() },
                        images = images.map { it.toImage() }
                    )
                )
            }
        } else {
            getErrorResponse(errorBody = (spotifyResponse as SpotifyApiResponse.Error).body)
        }
    }

    override suspend fun getAlbumTracks(id: String, offset: Int, limit: Int): Response {
        val spotifyResponse = withContext(Dispatchers.IO) {
            spotifyApi.getAlbumTracks(id, offset, limit)
        }

        return if (spotifyResponse is SpotifyApiResponse.Success) {
            with(spotifyResponse.result) {
                getSuccessResponse(
                    data = GetAlbumTracksResponse(
                        isNext = isNext,
                        tracks = items.map { it.toTrackInfo() }
                    )
                )
            }
        } else {
            getErrorResponse(errorBody = (spotifyResponse as SpotifyApiResponse.Error).body)
        }
    }

}