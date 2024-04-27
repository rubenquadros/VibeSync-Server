package io.github.rubenquadros.vibesync.server.util

import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse

fun List<SpotifyApiResponse<*, ErrorBody>>.toApiResponse(block: (successResults: List<Any>) -> Response): Response {

    val successResults = mutableListOf<Any>()

    forEach { spotifyApiResponse: SpotifyApiResponse<*, ErrorBody> ->
        if (spotifyApiResponse is SpotifyApiResponse.Error) {
            return getErrorResponse(spotifyApiResponse.body)
        } else {
            successResults.add((spotifyApiResponse as SpotifyApiResponse.Success).result!!)
        }
    }
    return block(successResults.toList())
}