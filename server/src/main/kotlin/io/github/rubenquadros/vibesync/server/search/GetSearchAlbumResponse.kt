package io.github.rubenquadros.vibesync.server.search

import io.github.rubenquadros.vibesync.server.album.GetAlbumResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchAlbumResponse(
    @SerialName("isNext")
    val isNext: Boolean,
    @SerialName("albums")
    val albums: List<GetAlbumResponse>
)