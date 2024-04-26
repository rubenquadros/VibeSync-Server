package io.github.rubenquadros.vibesync.server.model

import io.github.rubenquadros.kovibes.api.response.Image
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

fun Image.toImage(): io.github.rubenquadros.vibesync.server.model.Image {
    return io.github.rubenquadros.vibesync.server.model.Image(
        height = height,
        width = width,
        url = url
    )
}