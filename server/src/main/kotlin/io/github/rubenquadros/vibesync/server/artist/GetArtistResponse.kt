package io.github.rubenquadros.vibesync.server.artist

import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.vibesync.server.model.ArtistInfo
import io.github.rubenquadros.vibesync.server.model.MediaInfo
import io.github.rubenquadros.vibesync.server.model.TrackInfo
import io.github.rubenquadros.vibesync.server.model.toImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetArtistResponse(
    @SerialName("artistInfo")
    val artistInfo: ArtistInfo,
    @SerialName("topTracks")
    val topTracks: List<TrackInfo>,
    @SerialName("albums")
    val albums: List<MediaInfo>,
    @SerialName("relatedArtists")
    val relatedArtists: List<MediaInfo>
)

fun Artist.toArtistInfo(): ArtistInfo {
    return ArtistInfo(
        id = id,
        name = name,
        genres = genres,
        images = images?.map { it.toImage() }.orEmpty()
    )
}