package io.github.rubenquadros.vibesync.kovibes

import io.github.rubenquadros.kovibes.api.KoVibesApi
import io.github.rubenquadros.kovibes.api.SpotifyService
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
import org.koin.core.annotation.Single

@Single
class SpotifyApiImpl : SpotifyApi {

    private val spotifyService: SpotifyService by lazy {
        //init kovibes
        val clientId = System.getenv("CLIENT_ID")
        val clientSecret = System.getenv("CLIENT_SECRET")
        KoVibesApi.createSpotifyService("173791e72eeb42cfb4ed1fd14aebcdcb", "a14101811d6941099bbfb8fdfa363cc2")
    }

    override suspend fun getFeaturedPlaylists(): SpotifyApiResponse<Playlists, ErrorBody> {
        return spotifyService.getFeaturedPlaylists()
    }

    override suspend fun getArtist(id: String): SpotifyApiResponse<Artist, ErrorBody> {
        return spotifyService.getArtist(id)
    }

    override suspend fun getArtistAlbums(id: String): SpotifyApiResponse<Albums, ErrorBody> {
        return spotifyService.getArtistAlbums(id)
    }

    override suspend fun getArtistTopTracks(id: String): SpotifyApiResponse<ArtistTopTracks, ErrorBody> {
        return spotifyService.getArtistTopTracks(id)
    }

    override suspend fun getRelatedArtists(id: String): SpotifyApiResponse<RelatedArtists, ErrorBody> {
        return spotifyService.getRelatedArtists(id)
    }

    override suspend fun getAlbum(id: String): SpotifyApiResponse<Album, ErrorBody> {
        return spotifyService.getAlbum(id)
    }

    override suspend fun getAlbumTracks(
        id: String,
        offset: Int,
        limit: Int
    ): SpotifyApiResponse<AlbumTracks, ErrorBody> {
        return spotifyService.getAlbumTracks(id = id, offset = offset, limit = limit)
    }

    override suspend fun getPlaylist(id: String): SpotifyApiResponse<Playlist, ErrorBody> {
        return spotifyService.getPlaylist(id)
    }

    override suspend fun getPlaylistTracks(id: String, offset: Int, limit: Int): SpotifyApiResponse<PlaylistTracks, ErrorBody> {
        return spotifyService.getPlaylistTracks(id = id, offset = offset, limit = limit)
    }

    override suspend fun searchTrack(query: String, offset: Int, limit: Int): SpotifyApiResponse<Tracks, ErrorBody> {
        return spotifyService.searchTrack(query = query, offset = offset, limit = limit)
    }

    override suspend fun searchArtist(query: String, offset: Int, limit: Int): SpotifyApiResponse<Artists, ErrorBody> {
        return spotifyService.searchArtist(query = query, offset = offset, limit = limit)
    }

    override suspend fun searchAlbum(query: String, offset: Int, limit: Int): SpotifyApiResponse<Albums, ErrorBody> {
        return spotifyService.searchAlbum(query = query, offset = offset, limit = limit)
    }

    override suspend fun searchPlaylist(
        query: String,
        offset: Int,
        limit: Int
    ): SpotifyApiResponse<Playlists, ErrorBody> {
        return spotifyService.searchPlaylist(query = query, offset = offset, limit = limit)
    }
}