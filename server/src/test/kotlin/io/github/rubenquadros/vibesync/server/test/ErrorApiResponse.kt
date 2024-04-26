package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.vibesync.server.model.Error
import io.github.rubenquadros.vibesync.server.model.Response
import io.ktor.http.HttpStatusCode

val errorApiResponse = Response(
    status = HttpStatusCode.InternalServerError,
    data = Error(message = "Something went wrong.")
)