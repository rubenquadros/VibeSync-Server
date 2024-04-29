package io.github.rubenquadros.vibesync.server.test.search

import io.github.rubenquadros.vibesync.server.album.toAlbumResponse
import io.github.rubenquadros.vibesync.server.artist.toArtistInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getServerErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.model.toTrackInfo
import io.github.rubenquadros.vibesync.server.playlist.toPlaylistResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchAlbumResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchArtistResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchPlaylistResponse
import io.github.rubenquadros.vibesync.server.search.GetSearchTrackResponse
import io.github.rubenquadros.vibesync.server.search.SearchApi
import io.github.rubenquadros.vibesync.server.test.errorMessage
import io.github.rubenquadros.vibesync.test.data.geTracks
import io.github.rubenquadros.vibesync.test.data.getAlbums
import io.github.rubenquadros.vibesync.test.data.getArtists
import io.github.rubenquadros.vibesync.test.data.getPlaylists

class FakeSearchApi : SearchApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun searchTrack(query: String, offset: Int, limit: Int): Response {
        return if (isError) {
            getServerErrorResponse(errorMessage)
        } else {
            getSuccessResponse(
                data = GetSearchTrackResponse(
                    isNext = true,
                    tracks = geTracks().map { it.toTrackInfo() }
                )
            )
        }
    }

    override suspend fun searchArtist(query: String, offset: Int, limit: Int): Response {
        return if (isError) {
            getServerErrorResponse(errorMessage)
        } else {
            getSuccessResponse(
                data = GetSearchArtistResponse(
                    isNext = true,
                    artists = getArtists().map { it.toArtistInfo() }
                )
            )
        }
    }

    override suspend fun searchAlbum(query: String, offset: Int, limit: Int): Response {
        return if (isError) {
            getServerErrorResponse(errorMessage)
        } else {
            getSuccessResponse(
                data = GetSearchAlbumResponse(
                    isNext = true,
                    albums = getAlbums().map { it.toAlbumResponse() }
                )
            )
        }
    }

    override suspend fun searchPlaylist(query: String, offset: Int, limit: Int): Response {
        return if (isError) {
            getServerErrorResponse(errorMessage)
        } else {
            getSuccessResponse(
                data = GetSearchPlaylistResponse(
                    isNext = true,
                    playlists = getPlaylists().map { it.toPlaylistResponse() }
                )
            )
        }
    }
}