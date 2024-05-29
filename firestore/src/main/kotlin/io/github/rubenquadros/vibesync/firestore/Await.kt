package io.github.rubenquadros.vibesync.firestore

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.ListenerRegistration
import com.google.cloud.firestore.Query
import com.google.cloud.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal suspend fun Query.await(): List<QueryDocumentSnapshot> {

    var listener: ListenerRegistration? = null

    fun cleanup() {
        listener?.remove()
        listener = null
    }

    val result = suspendCancellableCoroutine { cancellableContinuation ->

        cancellableContinuation.invokeOnCancellation { cleanup() }

        listener = this.addSnapshotListener { value, error ->
            cleanup()
            if (error != null) {
                cancellableContinuation.resumeWithException(error)
            } else {
                cancellableContinuation.resume(value!!.documents)
            }
        }
    }

    return result
}

internal suspend fun DocumentReference.await(): Map<String, Any>? {
    var listener: ListenerRegistration? = null

    fun cleanup() {
        listener?.remove()
        listener = null
    }

    val result = suspendCancellableCoroutine { cancellableContinuation ->

        cancellableContinuation.invokeOnCancellation { cleanup() }

        listener = this.addSnapshotListener { value, error ->
            cleanup()
            if (error != null) {
                cancellableContinuation.resumeWithException(error)
            } else {
                cancellableContinuation.resume(value!!.data)
            }
        }
    }

    return result
}

internal suspend fun <T>ApiFuture<T>.await(): T {
    val result = suspendCancellableCoroutine { cancellableContinuation ->
        val executor = Executors.newSingleThreadExecutor()

        cancellableContinuation.invokeOnCancellation {
            this.cancel(true)
            executor.shutdown()
        }

        this.addListener({
            try {
                val writeResult = this.get()
                cancellableContinuation.resume(writeResult)
            } catch (e: Exception) {
                cancellableContinuation.resumeWithException(e)
            } finally {
                executor.shutdown()
            }
        }, executor)
    }

    return result
}