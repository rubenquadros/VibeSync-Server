package io.github.rubenquadros.vibesync.kovibes

import io.github.rubenquadros.kovibes.api.response.Albums
import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.kovibes.api.response.ArtistTopTracks
import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.RelatedArtists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse

interface SpotifyApi {
    suspend fun getFeaturedPlaylists(): SpotifyApiResponse<Playlists, ErrorBody>
    suspend fun getArtist(id: String): SpotifyApiResponse<Artist, ErrorBody>
    suspend fun getArtistAlbums(id: String): SpotifyApiResponse<Albums, ErrorBody>
    suspend fun getArtistTopTracks(id: String): SpotifyApiResponse<ArtistTopTracks, ErrorBody>
    suspend fun getRelatedArtists(id: String): SpotifyApiResponse<RelatedArtists, ErrorBody>
}