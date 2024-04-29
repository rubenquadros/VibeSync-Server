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
import io.github.rubenquadros.vibesync.test.data.albumResponse
import io.github.rubenquadros.vibesync.test.data.albumTracksResponse
import io.github.rubenquadros.vibesync.test.data.albumsResponse
import io.github.rubenquadros.vibesync.test.data.artistResponse
import io.github.rubenquadros.vibesync.test.data.artistTopTracksResponse
import io.github.rubenquadros.vibesync.test.data.featuredPlaylistsResponse
import io.github.rubenquadros.vibesync.test.data.playlistResponse
import io.github.rubenquadros.vibesync.test.data.playlistTracksResponse
import io.github.rubenquadros.vibesync.test.data.relatedArtistsResponse
import io.github.rubenquadros.vibesync.test.data.searchAlbumResponse
import io.github.rubenquadros.vibesync.test.data.searchArtistResponse
import io.github.rubenquadros.vibesync.test.data.searchPlaylistResponse
import io.github.rubenquadros.vibesync.test.data.searchTrackResponse

class FakeSpotifyApi : SpotifyApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getFeaturedPlaylists(): SpotifyApiResponse<Playlists, ErrorBody> {
        return getSpotifyResponse(isError, featuredPlaylistsResponse)
    }

    override suspend fun getArtist(id: String): SpotifyApiResponse<Artist, ErrorBody> {
        return getSpotifyResponse(isError, artistResponse)
    }

    override suspend fun getArtistAlbums(id: String): SpotifyApiResponse<Albums, ErrorBody> {
        return getSpotifyResponse(isError, albumsResponse)
    }

    override suspend fun getArtistTopTracks(id: String): SpotifyApiResponse<ArtistTopTracks, ErrorBody> {
        return getSpotifyResponse(isError, artistTopTracksResponse)
    }

    override suspend fun getRelatedArtists(id: String): SpotifyApiResponse<RelatedArtists, ErrorBody> {
        return getSpotifyResponse(isError, relatedArtistsResponse)
    }

    override suspend fun getAlbum(id: String): SpotifyApiResponse<Album, ErrorBody> {
        return getSpotifyResponse(isError, albumResponse)
    }

    override suspend fun getAlbumTracks(
        id: String,
        offset: Int,
        limit: Int
    ): SpotifyApiResponse<AlbumTracks, ErrorBody> {
        return getSpotifyResponse(isError, albumTracksResponse)
    }

    override suspend fun getPlaylist(id: String): SpotifyApiResponse<Playlist, ErrorBody> {
        return getSpotifyResponse(isError, playlistResponse)
    }

    override suspend fun getPlaylistTracks(id: String, offset: Int, limit: Int): SpotifyApiResponse<PlaylistTracks, ErrorBody> {
        return getSpotifyResponse(isError, playlistTracksResponse)
    }

    override suspend fun searchTrack(query: String, offset: Int, limit: Int): SpotifyApiResponse<Tracks, ErrorBody> {
        return getSpotifyResponse(isError, searchTrackResponse)
    }

    override suspend fun searchArtist(query: String, offset: Int, limit: Int): SpotifyApiResponse<Artists, ErrorBody> {
        return getSpotifyResponse(isError, searchArtistResponse)
    }

    override suspend fun searchAlbum(query: String, offset: Int, limit: Int): SpotifyApiResponse<Albums, ErrorBody> {
        return getSpotifyResponse(isError, searchAlbumResponse)
    }

    override suspend fun searchPlaylist(
        query: String,
        offset: Int,
        limit: Int
    ): SpotifyApiResponse<Playlists, ErrorBody> {
        return getSpotifyResponse(isError, searchPlaylistResponse)
    }
}