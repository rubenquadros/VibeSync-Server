package io.github.rubenquadros.vibesync.server.album

import io.github.rubenquadros.kovibes.api.response.Album
import io.github.rubenquadros.shared.models.Image
import io.github.rubenquadros.vibesync.kovibes.toImage
import io.github.rubenquadros.vibesync.server.artist.toArtistInfo
import io.github.rubenquadros.vibesync.server.model.ArtistInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAlbumResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("artists")
    val artists: List<ArtistInfo>,
    @SerialName("releaseDate")
    val releaseDate: String,
    @SerialName("albumType")
    val albumType: String,
    @SerialName("images")
    val images: List<Image>
)

fun Album.toAlbumResponse(): GetAlbumResponse {
    return GetAlbumResponse(
        id = id,
        name = name,
        releaseDate = releaseDate,
        albumType = albumType,
        artists = artists.map { it.toArtistInfo() },
        images = images.map { it.toImage() }
    )
}