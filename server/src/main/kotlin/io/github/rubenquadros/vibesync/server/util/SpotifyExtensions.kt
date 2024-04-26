package io.github.rubenquadros.vibesync.server.util

import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.server.model.Error
import io.github.rubenquadros.vibesync.server.model.Response
import io.ktor.http.HttpStatusCode

fun List<SpotifyApiResponse<*, ErrorBody>>.toApiResponse(block: (successResults: List<Any>) -> Response): Response {

    val successResults = mutableListOf<Any>()

    forEach { spotifyApiResponse: SpotifyApiResponse<*, ErrorBody> ->
        if (spotifyApiResponse is SpotifyApiResponse.Error) {
            return with(spotifyApiResponse.body.error) {
                Response(
                    status = HttpStatusCode(status, message),
                    data = Error(message = message)
                )
            }
        } else {
            successResults.add((spotifyApiResponse as SpotifyApiResponse.Success).result!!)
        }
    }
    return block(successResults.toList())
}