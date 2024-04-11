package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.vibesync.server.configureRouting
import io.github.rubenquadros.vibesync.server.configureSerialization
import io.github.rubenquadros.vibesync.server.model.Error
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.test.startTestApplication
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json


fun testApplication(block: suspend (client: HttpClient) -> Unit) = startTestApplication {
    it.application {
        configureSerialization()
        configureRouting()
    }
    val client = it.createClient {
        install(ContentNegotiation) {
            json()
        }
    }
    block(client)
}

inline fun <reified T>Response.assertSuccess(block: (data: T) -> Unit) {
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