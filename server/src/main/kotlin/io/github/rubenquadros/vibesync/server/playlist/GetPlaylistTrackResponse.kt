package io.github.rubenquadros.vibesync.server.playlist

import io.github.rubenquadros.vibesync.server.model.TrackInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPlaylistTrackResponse(
    @SerialName("isNext")
    val isNext: Boolean,
    @SerialName("tracks")
    val tracks: List<TrackInfo>
)
