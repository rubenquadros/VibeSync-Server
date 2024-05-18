package io.github.rubenquadros.vibesync.firestore.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistInfo(
    val id: String,
    val playlistName: String,
    val owner: String,
    val image: String? = null
)
