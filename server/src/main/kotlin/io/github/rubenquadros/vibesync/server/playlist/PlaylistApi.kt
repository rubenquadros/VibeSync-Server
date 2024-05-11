package io.github.rubenquadros.vibesync.server.playlist

import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.server.model.GetPaginatedResponse
import io.github.rubenquadros.vibesync.server.model.PlaylistTracks
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.model.toImage
import io.github.rubenquadros.vibesync.server.model.toTrackInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface PlaylistApi {
    suspend fun getPlaylist(id: String): Response
    suspend fun getPlaylistTracks(id: String, offset: Int, limit: Int): Response
}

@Single
class PlaylistApiImpl(private val spotifyApi: SpotifyApi) : PlaylistApi {
    override suspend fun getPlaylist(id: String): Response {
        val spotifyResponse = withContext(Dispatchers.IO) {
            spotifyApi.getPlaylist(id)
        }

        return if (spotifyResponse is SpotifyApiResponse.Success) {
            with(spotifyResponse.result) {
                getSuccessResponse(
                    data = GetPlaylistResponse(
                        id = id,
                        name = name,
                        images = images.map { it.toImage() }
                    )
                )
            }
        } else {
            getErrorResponse(errorBody = (spotifyResponse as SpotifyApiResponse.Error).body)
        }
    }

    override suspend fun getPlaylistTracks(id: String, offset: Int, limit: Int): Response {
        val spotifyResponse = withContext(Dispatchers.IO) {
            spotifyApi.getPlaylistTracks(id, offset, limit)
        }

        return if (spotifyResponse is SpotifyApiResponse.Success) {
            with(spotifyResponse.result) {
                getSuccessResponse(
                    data = GetPaginatedResponse(
                        isNext = isNext,
                        content = PlaylistTracks(tracks = tracks.map { it.toTrackInfo() })
                    )
                )
            }
        } else {
            getErrorResponse(errorBody = (spotifyResponse as SpotifyApiResponse.Error).body)
        }
    }

}