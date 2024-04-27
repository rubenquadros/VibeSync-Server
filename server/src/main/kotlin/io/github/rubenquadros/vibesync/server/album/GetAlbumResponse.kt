package io.github.rubenquadros.vibesync.server.album

import io.github.rubenquadros.vibesync.server.model.ArtistInfo
import io.github.rubenquadros.vibesync.server.model.Image
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
