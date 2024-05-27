package io.github.rubenquadros.vibesync.firestore

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.WriteResult
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

internal fun writeData(block: () -> ApiFuture<WriteResult>) {
    val result = block()
    val writeResult = result.get()
    println("Write operation time: ${writeResult.updateTime}")
}

internal fun writeBatch(block: () -> ApiFuture<List<WriteResult>>) {
    val results = block().get()
    results.forEachIndexed { index, writeResult ->
        println("Write operation $index time: ${writeResult.updateTime}")
    }
}