package io.github.rubenquadros.vibesync.server.model

import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

data class Response(
    val status: HttpStatusCode,
    val data: Any
)

@Serializable
data class Error(
    val message: String
)

fun getErrorResponse(message: String, status: HttpStatusCode) = Response(
    status = status,
    data = Error(message)
)

fun getErrorResponse(errorBody: ErrorBody) = with(errorBody.error) {
    Response(
        status = HttpStatusCode(status, message),
        data = Error(message = message)
    )
}

fun getSuccessResponse(data: Any) = Response(
    status = HttpStatusCode.OK,
    data = data
)

fun getEmptyBodySuccessResponse() = Response(
    status = HttpStatusCode.OK,
    data = "Success"
)