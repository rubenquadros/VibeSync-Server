package io.github.rubenquadros.vibesync.firestore.model

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo

sealed interface FirestorePaginatedResponse<out T> {
    val isNext: Boolean
    val items: List<T>
}

data class TracksPaginatedResponse(
    override val isNext: Boolean,
    override val items: List<TrackInfo>
) : FirestorePaginatedResponse<TrackInfo>

data class LikedAlbumsPaginatedResponse(
    override val isNext: Boolean,
    override val items: List<MediaInfo>
) : FirestorePaginatedResponse<MediaInfo>

data class UserPlaylistsPaginatedResponse(
    override val isNext: Boolean,
    override val items: List<PlaylistInfo>
) : FirestorePaginatedResponse<PlaylistInfo>
