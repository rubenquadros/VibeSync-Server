package io.github.rubenquadros.vibesync.firestore

import com.google.cloud.firestore.DocumentSnapshot
import io.github.rubenquadros.shared.models.Image

@Suppress("UNCHECKED_CAST")
internal fun DocumentSnapshot.getDataMap(): Map<String, Any> {
    return this["data"] as Map<String, Any>
}

@Suppress("UNCHECKED_CAST")
internal fun DocumentSnapshot.getImages(): List<Image> {
    return (this["images"] as List<Map<*, *>>).map {
        Image(
            width = (it["width"] as Long).toInt(),
            height = (it["height"] as Long).toInt(),
            url = it["url"] as? String
        )
    }
}

@Suppress("UNCHECKED_CAST")
internal fun Map<String, Any>.getImages(): List<Image> {
    return (this["images"] as List<Map<*, *>>).map {
        Image(
            width = (it["width"] as Long).toInt(),
            height = (it["height"] as Long).toInt(),
            url = it["url"] as? String
        )
    }
}