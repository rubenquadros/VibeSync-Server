package io.github.rubenquadros.vibesync.server.artist

import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.kovibes.api.response.Track
import io.github.rubenquadros.vibesync.server.model.Image
import io.github.rubenquadros.vibesync.server.model.MediaInfo
import io.github.rubenquadros.vibesync.server.model.toImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetArtistResponse(
    @SerialName("artistInfo")
    val artistInfo: ArtistInfo,
    @SerialName("topTracks")
    val topTracks: List<TopTrackInfo>,
    @SerialName("albums")
    val albums: List<MediaInfo>,
    @SerialName("relatedArtists")
    val relatedArtists: List<MediaInfo>
)

@Serializable
data class ArtistInfo(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("images")
    val images: List<Image>,
    @SerialName("genres")
    val genres: List<String>? = null
)

@Serializable
data class TopTrackInfo(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("preview_url")
    val previewUrl: String? = null,
    @SerialName("images")
    val images: List<Image>
)

fun Artist.toArtistInfo(): ArtistInfo {
    return ArtistInfo(
        id = id,
        name = name,
        genres = genres,
        images = images?.map { it.toImage() }.orEmpty()
    )
}

fun Track.toTopTrackInfo(): TopTrackInfo {
    return TopTrackInfo(
        id = id,
        name = name,
        previewUrl = previewUrl,
        images = album.images.map { it.toImage() }
    )
}