package io.github.rubenquadros.vibesync.server.user.like

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getEmptyBodySuccessResponse
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import org.koin.core.annotation.Single

interface LikeApi {
    suspend fun likeTrack(userId: String, trackInfo: TrackInfo): Response

    suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): Response

    suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): Response

    suspend fun getLikedTracks(userId: String): Response

    suspend fun getLikedAlbums(userId: String): Response
}

@Single
class LikeApiImpl(private val firestoreApi: FirestoreApi) : LikeApi {
    override suspend fun likeTrack(userId: String, trackInfo: TrackInfo): Response {
        val response = firestoreApi.likeTrack(userId, trackInfo)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): Response {
        val response = firestoreApi.likeAlbum(userId, mediaInfo)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): Response {
        val response = firestoreApi.likePlaylist(userId, mediaInfo)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun getLikedTracks(userId: String): Response {
        val response = firestoreApi.getLikedTracks(userId)

        return if (response is FirestoreApiResponse.Success) {
            getSuccessResponse(response.data)
        } else {
            val errorResponse = response as FirestoreApiResponse.Error
            getErrorResponse(status = errorResponse.statusCode, message = errorResponse.throwable.message.toString())
        }
    }

    override suspend fun getLikedAlbums(userId: String): Response {
        val response = firestoreApi.getLikedAlbums(userId)

        return if (response is FirestoreApiResponse.Success) {
            getSuccessResponse(response.data)
        } else {
            val errorResponse = response as FirestoreApiResponse.Error
            getErrorResponse(status = errorResponse.statusCode, message = errorResponse.throwable.message.toString())
        }
    }
}