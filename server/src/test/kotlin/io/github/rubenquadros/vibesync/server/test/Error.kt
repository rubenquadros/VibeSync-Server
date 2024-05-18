package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.ktor.http.HttpStatusCode

val apiErrorResponse = getErrorResponse(
    message = "Something went wrong.",
    status = HttpStatusCode.InternalServerError
)

