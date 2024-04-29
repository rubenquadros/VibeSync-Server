package io.github.rubenquadros.vibesync.server.search

import io.github.rubenquadros.vibesync.server.model.TrackInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchTrackResponse(
    @SerialName("isNext")
    val isNext: Boolean,
    @SerialName("tracks")
    val tracks: List<TrackInfo>
)
