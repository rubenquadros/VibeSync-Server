package io.github.rubenquadros.vibesync.firestore.model

import io.ktor.http.HttpStatusCode

sealed interface FirestoreApiResponse<out RESPONSE> {

    data class Success<out RESPONSE>(val data: RESPONSE): FirestoreApiResponse<RESPONSE>

    data object SuccessNoBody : FirestoreApiResponse<Nothing>

    data class Error(val statusCode: HttpStatusCode, val throwable: Throwable): FirestoreApiResponse<Nothing>
}

fun <T>getSuccessResponse(data: T) = FirestoreApiResponse.Success(data)

fun getSuccessWithoutBody() = FirestoreApiResponse.SuccessNoBody

fun getErrorResponse(
    throwable: Throwable,
    statusCode: HttpStatusCode = HttpStatusCode.InternalServerError
) = FirestoreApiResponse.Error(statusCode, throwable)