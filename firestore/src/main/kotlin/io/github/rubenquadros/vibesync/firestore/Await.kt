package io.github.rubenquadros.vibesync.firestore

import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.ListenerRegistration
import com.google.cloud.firestore.Query
import com.google.cloud.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal suspend fun Query.await(): List<QueryDocumentSnapshot> {

    var listener: ListenerRegistration? = null

    fun cleanup() {
        listener?.remove()
        listener = null
    }

    val result = suspendCancellableCoroutine { cancellableContinuation ->
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