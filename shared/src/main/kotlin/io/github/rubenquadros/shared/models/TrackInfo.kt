package io.github.rubenquadros.shared.models

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
