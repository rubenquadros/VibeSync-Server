package io.github.rubenquadros.vibesync.server.model

import io.github.rubenquadros.kovibes.api.response.Album
import io.github.rubenquadros.kovibes.api.response.Artist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaInfo(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("images")
    val images: List<Image>
)

fun Album.toMediaInfo(): MediaInfo {
    return MediaInfo(
        id = id,
        name = name,
        images = images.map { it.toImage() }
    )
}

fun Artist.toMediaInfo(): MediaInfo {
    return MediaInfo(
        id = id,
        name = name,
        images = images?.map { it.toImage() }.orEmpty()
    )
}