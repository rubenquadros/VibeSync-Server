package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.kovibes.api.response.Album
import io.github.rubenquadros.kovibes.api.response.AlbumTracks
import io.github.rubenquadros.kovibes.api.response.Albums
import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.kovibes.api.response.ArtistTopTracks
import io.github.rubenquadros.kovibes.api.response.Artists
import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlist
import io.github.rubenquadros.kovibes.api.response.PlaylistTracks
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.RelatedArtists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.kovibes.api.response.Tracks
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.test.TestApi
import io.github.rubenquadros.vibesync.test.data.album1
import io.github.rubenquadros.vibesync.test.data.albumTracksResponse
import io.github.rubenquadros.vibesync.test.data.albumsResponse
import io.github.rubenquadros.vibesync.test.data.artist1
import io.github.rubenquadros.vibesync.test.data.artistTopTracksResponse
import io.github.rubenquadros.vibesync.test.data.featuredPlaylistsResponse
import io.github.rubenquadros.vibesync.test.data.playlist1
import io.github.rubenquadros.vibesync.test.data.playlistTracksResponse
import io.github.rubenquadros.vibesync.test.data.relatedArtistsResponse
import io.github.rubenquadros.vibesync.test.data.searchAlbumResponse
import io.github.rubenquadros.vibesync.test.data.searchArtistResponse
import io.github.rubenquadros.vibesync.test.data.searchPlaylistResponse
import io.github.rubenquadros.vibesync.test.data.searchTrackResponse
import io.github.rubenquadros.vibesync.test.data.spotifyErrorResponse

abstract class TestSpotifyApi : TestApi(), SpotifyApi {
    fun <R>getSpotifyResponse(
        response: R
    ): SpotifyApiResponse<R, ErrorBody> {
        return if (isError) {
            spotifyErrorResponse
        } else {
            SpotifyApiResponse.Success(response)
        }
    }
}

class FakeSpotifyApi : TestSpotifyApi() {
    override suspend fun getFeaturedPlaylists(): SpotifyApiResponse<Playlists, ErrorBody> {
        return getSpotifyResponse(featuredPlaylistsResponse)
    }

    override suspend fun getArtist(id: String): SpotifyApiResponse<Artist, ErrorBody> {
        return getSpotifyResponse(artist1)
    }

    override suspend fun getArtistAlbums(id: String): SpotifyApiResponse<Albums, ErrorBody> {
        return getSpotifyResponse(albumsResponse)
    }

    override suspend fun getArtistTopTracks(id: String): SpotifyApiResponse<ArtistTopTracks, ErrorBody> {
        return getSpotifyResponse(artistTopTracksResponse)
    }

    override suspend fun getRelatedArtists(id: String): SpotifyApiResponse<RelatedArtists, ErrorBody> {
        return getSpotifyResponse(relatedArtistsResponse)
    }

    override suspend fun getAlbum(id: String): SpotifyApiResponse<Album, ErrorBody> {
        return getSpotifyResponse(album1)
    }

    override suspend fun getAlbumTracks(
        id: String,
        offset: Int,
        limit: Int
    ): SpotifyApiResponse<AlbumTracks, ErrorBody> {
        return getSpotifyResponse(albumTracksResponse)
    }

    override suspend fun getPlaylist(id: String): SpotifyApiResponse<Playlist, ErrorBody> {
        return getSpotifyResponse(playlist1)
    }

    override suspend fun getPlaylistTracks(id: String, offset: Int, limit: Int): SpotifyApiResponse<PlaylistTracks, ErrorBody> {
        return getSpotifyResponse(playlistTracksResponse)
    }

    override suspend fun searchTrack(query: String, offset: Int, limit: Int): SpotifyApiResponse<Tracks, ErrorBody> {
        return getSpotifyResponse(searchTrackResponse)
    }

    override suspend fun searchArtist(query: String, offset: Int, limit: Int): SpotifyApiResponse<Artists, ErrorBody> {
        return getSpotifyResponse(searchArtistResponse)
    }

    override suspend fun searchAlbum(query: String, offset: Int, limit: Int): SpotifyApiResponse<Albums, ErrorBody> {
        return getSpotifyResponse(searchAlbumResponse)
    }

    override suspend fun searchPlaylist(
        query: String,
        offset: Int,
        limit: Int
    ): SpotifyApiResponse<Playlists, ErrorBody> {
        return getSpotifyResponse(searchPlaylistResponse)
    }
}