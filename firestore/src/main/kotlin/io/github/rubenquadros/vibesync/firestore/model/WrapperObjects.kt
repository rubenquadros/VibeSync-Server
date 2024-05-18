package io.github.rubenquadros.vibesync.firestore.model

import com.google.cloud.firestore.annotation.ServerTimestamp
import java.util.Date

data class WriteWrapper(
    val data: Any,
    @ServerTimestamp
    val timestamp: Date? = null
)

data class UpdateWrapper(
    val data: Any,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

fun Any.toWriteObject(): WriteWrapper = WriteWrapper(this)

fun Any.toUpdateObject(): UpdateWrapper = UpdateWrapper(this)