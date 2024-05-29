package io.github.rubenquadros.vibesync.firestore

import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.getErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal suspend fun <R>getFirestoreResponse(block: suspend () -> FirestoreApiResponse<R>): FirestoreApiResponse<R> {
    return runCatching {
        withContext(Dispatchers.IO) {
            block()
        }
    }.getOrElse {
        return getErrorResponse(throwable = it)
    }
}