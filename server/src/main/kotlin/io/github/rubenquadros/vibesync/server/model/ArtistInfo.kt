package io.github.rubenquadros.vibesync.server.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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