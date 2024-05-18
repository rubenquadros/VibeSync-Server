package io.github.rubenquadros.vibesync.server.util

import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse

fun List<SpotifyApiResponse<*, ErrorBody>>.toSpotifySuccessData(block: (successResults: List<Any>) -> Response): Response {

    val successResults = mutableListOf<Any>()

    forEach { spotifyApiResponse: SpotifyApiResponse<*, ErrorBody> ->
        if (spotifyApiResponse is SpotifyApiResponse.Error) {
            return getErrorResponse(spotifyApiResponse.body)
        } else {
            successResults.add((spotifyApiResponse as SpotifyApiResponse.Success).result!!)
        }
    }
    return block(successResults)
}

fun List<FirestoreApiResponse<*>>.toFirestoreSuccessData(block: (successResults: List<Any>) -> Response): Response {

    val successResults = mutableListOf<Any>()

    forEach { firestoreApiResponse: FirestoreApiResponse<*> ->
        if (firestoreApiResponse is FirestoreApiResponse.Error) {
            return getErrorResponse(
                status = firestoreApiResponse.statusCode,
                message = firestoreApiResponse.throwable.message.toString()
            )
        } else {
            successResults.add((firestoreApiResponse as FirestoreApiResponse.Success).data!!)
        }
    }

    return block(successResults)
}