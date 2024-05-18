package io.github.rubenquadros.shared.models

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