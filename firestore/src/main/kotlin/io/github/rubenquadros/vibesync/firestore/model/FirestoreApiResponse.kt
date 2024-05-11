package io.github.rubenquadros.vibesync.firestore.model

sealed interface FirestoreApiResponse<out RESPONSE> {

    data class Success<out RESPONSE>(val data: RESPONSE): FirestoreApiResponse<RESPONSE>

    data class Error(val throwable: Throwable? = null): FirestoreApiResponse<Nothing>
}

fun <T>getSuccessResponse(data: T) = FirestoreApiResponse.Success(data)

fun getErrorResponse(throwable: Throwable) = FirestoreApiResponse.Error(throwable)