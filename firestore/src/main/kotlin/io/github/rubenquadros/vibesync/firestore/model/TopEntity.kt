package io.github.rubenquadros.vibesync.firestore.model

import kotlinx.serialization.Serializable

@Serializable
data class TopEntity(
    val id: String,
    val name: String,
    val image: String? = null
)
