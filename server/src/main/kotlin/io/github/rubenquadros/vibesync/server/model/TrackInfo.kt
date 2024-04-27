package io.github.rubenquadros.vibesync.server.model

import io.github.rubenquadros.kovibes.api.response.AlbumTrack
import io.github.rubenquadros.kovibes.api.response.Track
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackInfo(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("preview_url")
    val previewUrl: String? = null,
    @SerialName("images")
    val images: List<Image>,
    @SerialName("duration")
    val duration: Long
)

fun Track.toTrackInfo(): TrackInfo {
    return TrackInfo(
        id = id,
        name = name,
        previewUrl = previewUrl,
        images = album.images.map { it.toImage() },
        duration = duration
    )
}

fun AlbumTrack.toTrackInfo(): TrackInfo {
    return TrackInfo(
        id = id,
        name = name,
        previewUrl = previewUrl,
        duration = duration,
        images = emptyList()
    )
}