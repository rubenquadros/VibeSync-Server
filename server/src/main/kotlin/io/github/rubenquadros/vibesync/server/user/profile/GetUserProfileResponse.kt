package io.github.rubenquadros.vibesync.server.user.profile

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import kotlinx.serialization.Serializable

@Serializable
data class GetUserProfileResponse(
    val profileInfo: UserProfile,
    val playlists: List<PlaylistInfo>? = null,
    val likedTracks: List<TrackInfo>? = null,
    val likedAlbums: List<MediaInfo>? = null
)
