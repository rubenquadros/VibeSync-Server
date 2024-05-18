package io.github.rubenquadros.vibesync.server.user.unlike

import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getEmptyBodySuccessResponse
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import org.koin.core.annotation.Single

interface UnlikeApi {
    suspend fun unlikeTrack(userId: String, trackId: String): Response

    suspend fun unlikeAlbum(userId: String, albumId: String): Response

    suspend fun unlikePlaylist(userId: String, playlistId: String): Response
}

@Single
class UnlikeApiImpl(private val firestoreApi: FirestoreApi) : UnlikeApi {
    override suspend fun unlikeTrack(userId: String, trackId: String): Response {
        val response = firestoreApi.unlikeTrack(userId, trackId)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun unlikeAlbum(userId: String, albumId: String): Response {
        val response = firestoreApi.unlikeAlbum(userId, albumId)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun unlikePlaylist(userId: String, playlistId: String): Response {
        val response = firestoreApi.unlikePlaylist(userId, playlistId)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }
}