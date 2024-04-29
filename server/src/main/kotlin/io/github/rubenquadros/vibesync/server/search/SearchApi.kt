package io.github.rubenquadros.vibesync.server.search

import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.server.album.toAlbumResponse
import io.github.rubenquadros.vibesync.server.artist.toArtistInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.github.rubenquadros.vibesync.server.model.getServerErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.model.toTrackInfo
import io.github.rubenquadros.vibesync.server.playlist.toPlaylistResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface SearchApi {
    suspend fun searchTrack(query: String, offset: Int, limit: Int): Response
    suspend fun searchArtist(query: String, offset: Int, limit: Int): Response
    suspend fun searchAlbum(query: String, offset: Int, limit: Int): Response
    suspend fun searchPlaylist(query: String, offset: Int, limit: Int): Response
}

@Single
class SearchApiImpl(private val spotifyApi: SpotifyApi) : SearchApi {
    override suspend fun searchTrack(query: String, offset: Int, limit: Int): Response {
        val spotifyResponse = runCatching {
            withContext(Dispatchers.IO) {
                spotifyApi.searchTrack(query, offset, limit)
            }
        }.getOrElse {
            return getServerErrorResponse(message = it.message.toString())
        }

        return if (spotifyResponse is SpotifyApiResponse.Success) {
            with(spotifyResponse.result) {
                getSuccessResponse(
                    data = GetSearchTrackResponse(
                        isNext = isNext,
                        tracks = items.map { it.toTrackInfo() }
                    )
                )
            }
        } else {
            getErrorResponse(errorBody = (spotifyResponse as SpotifyApiResponse.Error).body)
        }
    }

    override suspend fun searchArtist(query: String, offset: Int, limit: Int): Response {
        val spotifyResponse = runCatching {
            withContext(Dispatchers.IO) {
                spotifyApi.searchArtist(query, offset, limit)
            }
        }.getOrElse {
            return getServerErrorResponse(message = it.message.toString())
        }

        return if (spotifyResponse is SpotifyApiResponse.Success) {
            with(spotifyResponse.result) {
                getSuccessResponse(
                    data = GetSearchArtistResponse(
                        isNext = isNext,
                        artists = items.map { it.toArtistInfo() }
                    )
                )
            }
        } else {
            getErrorResponse(errorBody = (spotifyResponse as SpotifyApiResponse.Error).body)
        }
    }

    override suspend fun searchAlbum(query: String, offset: Int, limit: Int): Response {
        val spotifyResponse = runCatching {
            spotifyApi.searchAlbum(query, offset, limit)
        }.getOrElse {
            return getServerErrorResponse(message = it.message.toString())
        }

        return if (spotifyResponse is SpotifyApiResponse.Success) {
            with(spotifyResponse.result) {
                getSuccessResponse(
                    data = GetSearchAlbumResponse(
                        isNext = isNext,
                        albums = items.map { it.toAlbumResponse() }
                    )
                )
            }
        } else {
            getErrorResponse(errorBody = (spotifyResponse as SpotifyApiResponse.Error).body)
        }
    }

    override suspend fun searchPlaylist(query: String, offset: Int, limit: Int): Response {
        val spotifyResponse = runCatching {
            spotifyApi.searchPlaylist(query, offset, limit)
        }.getOrElse {
            return getServerErrorResponse(message = it.message.toString())
        }

        return if (spotifyResponse is SpotifyApiResponse.Success) {
            with(spotifyResponse.result) {
                getSuccessResponse(
                    data = GetSearchPlaylistResponse(
                        isNext = isNext,
                        playlists = items.map { it.toPlaylistResponse() }
                    )
                )
            }
        } else {
            getErrorResponse(errorBody = (spotifyResponse as SpotifyApiResponse.Error).body)
        }
    }


}