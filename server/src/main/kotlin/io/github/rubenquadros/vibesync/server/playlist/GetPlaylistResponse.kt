package io.github.rubenquadros.vibesync.server.playlist

import io.github.rubenquadros.kovibes.api.response.Playlist
import io.github.rubenquadros.vibesync.server.model.Image
import io.github.rubenquadros.vibesync.server.model.toImage
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

fun Playlist.toPlaylistResponse(): GetPlaylistResponse {
    return GetPlaylistResponse(
        id = id,
        name = name,
        images = images.map { it.toImage() }
    )
}