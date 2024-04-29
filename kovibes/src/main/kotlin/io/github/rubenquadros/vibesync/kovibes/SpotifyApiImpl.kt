package io.github.rubenquadros.vibesync.kovibes

import io.github.rubenquadros.kovibes.api.KoVibesApi
import io.github.rubenquadros.kovibes.api.SpotifyService
import io.github.rubenquadros.kovibes.api.response.Album
import io.github.rubenquadros.kovibes.api.response.AlbumTracks
import io.github.rubenquadros.kovibes.api.response.Albums
import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.kovibes.api.response.ArtistTopTracks
import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlist
import io.github.rubenquadros.kovibes.api.response.PlaylistTracks
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.RelatedArtists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import org.koin.core.annotation.Single

@Single
class SpotifyApiImpl : SpotifyApi {

    private val spotifyService: SpotifyService by lazy {
        //init kovibes
        val clientId = System.getenv("CLIENT_ID")
        val clientSecret = System.getenv("CLIENT_SECRET")
        KoVibesApi.createSpotifyService(clientId, clientSecret)
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
}