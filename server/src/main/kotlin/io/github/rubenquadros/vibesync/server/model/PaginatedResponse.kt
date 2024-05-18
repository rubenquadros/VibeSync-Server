package io.github.rubenquadros.vibesync.server.model

import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.album.GetAlbumResponse
import io.github.rubenquadros.vibesync.server.playlist.GetPlaylistResponse
import kotlinx.serialization.Serializable

@Serializable
sealed interface PaginatedItems

@Serializable
data class GetPaginatedResponse(
    val isNext: Boolean,
    val content: PaginatedItems
)

@Serializable
data class PlaylistTracks(
    val tracks: List<TrackInfo>
) : PaginatedItems

@Serializable
data class SearchTracks(
    val tracks: List<TrackInfo>
) : PaginatedItems

@Serializable
data class SearchArtists(
    val artists: List<ArtistInfo>
) : PaginatedItems

@Serializable
data class SearchAlbums(
    val albums: List<GetAlbumResponse>
) : PaginatedItems

@Serializable
data class SearchPlaylists(
    val playlists: List<GetPlaylistResponse>
) : PaginatedItems