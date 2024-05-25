package io.github.rubenquadros.vibesync.server.model

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.server.album.GetAlbumResponse
import io.github.rubenquadros.vibesync.server.playlist.GetPlaylistResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface PaginatedItems

@Serializable
data class GetPaginatedResponse(
    val isNext: Boolean,
    val content: PaginatedItems
)

@Serializable
@SerialName("track")
data class TracksPage(
    val items: List<TrackInfo>
) : PaginatedItems

@Serializable
@SerialName("media")
data class MediaPage(
    val items: List<MediaInfo>
) : PaginatedItems

@Serializable
@SerialName("userPlaylist")
data class PlaylistPage(
    val items: List<PlaylistInfo>
) : PaginatedItems

@Serializable
@SerialName("artist")
data class SearchArtists(
    val artists: List<ArtistInfo>
) : PaginatedItems

@Serializable
@SerialName("album")
data class SearchAlbums(
    val albums: List<GetAlbumResponse>
) : PaginatedItems

@Serializable
@SerialName("playlist")
data class SearchPlaylists(
    val playlists: List<GetPlaylistResponse>
) : PaginatedItems