package io.github.rubenquadros.vibesync.server.model

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

fun getErrorResponse(message: String) = Response(
    status = HttpStatusCode.InternalServerError,
    data = Error(message = message)
)