package io.github.rubenquadros.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("height")
    val height: Int? = null,
    @SerialName("width")
    val width: Int? = null,
    @SerialName("url")
    val url: String? = null
)