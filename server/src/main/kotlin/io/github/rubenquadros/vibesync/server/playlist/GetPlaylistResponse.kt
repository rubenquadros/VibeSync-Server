package io.github.rubenquadros.vibesync.server.playlist

import io.github.rubenquadros.vibesync.server.model.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPlaylistResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("images")
    val images: List<Image>
)