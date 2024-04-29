package io.github.rubenquadros.vibesync.server.search

import io.github.rubenquadros.vibesync.server.model.ArtistInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchArtistResponse(
    @SerialName("isNext")
    val isNext: Boolean,
    @SerialName("artists")
    val artists: List<ArtistInfo>
)
