package io.github.rubenquadros.vibesync.firestore.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val image: String? = null
)
