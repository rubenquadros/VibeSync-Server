package io.github.rubenquadros.vibesync.server.album

import io.github.rubenquadros.vibesync.server.model.TrackInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAlbumTracksResponse(
    @SerialName("isNext")
    val isNext: Boolean,
    @SerialName("tracks")
    val tracks: List<TrackInfo>
)
