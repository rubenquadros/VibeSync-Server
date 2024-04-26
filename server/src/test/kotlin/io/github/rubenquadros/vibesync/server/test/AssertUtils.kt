package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.vibesync.server.model.Error
import io.github.rubenquadros.vibesync.server.model.Response
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

inline fun <reified T> Response.assertSuccess(block: (data: T) -> Unit) {
    assert(status == HttpStatusCode.OK)
    assert(data is T)
    block(data as T)
}

fun Response.assertSpotifyFailure() {
    assert(status.value == 500)
    assert(status.description == "Something went wrong.")
    assert(data is Error)
    assert((data as Error).message == "Something went wrong.")
}

fun Response.assertFirestoreFailure(block: (error: Error) -> Unit) {
    assert(status == HttpStatusCode.InternalServerError)
    assert(data is Error)
    block(data as Error)
}

fun HttpResponse.assertOk() {
    assert(status == HttpStatusCode.OK)
}

suspend fun HttpResponse.assertError() {
    assert(status == HttpStatusCode.InternalServerError)

    val body = body<Error>()

    assert(body.message == "Something went wrong.")
}