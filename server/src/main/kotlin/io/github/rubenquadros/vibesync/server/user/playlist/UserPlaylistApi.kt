package io.github.rubenquadros.vibesync.server.user.playlist

import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getEmptyBodySuccessResponse
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import org.koin.core.annotation.Single

interface UserPlaylistApi {
    suspend fun createPlaylist(userId: String, userName: String, playlistName: String, trackInfo: TrackInfo): Response

    suspend fun getUserPlaylists(userId: String): Response

    suspend fun deletePlaylist(userId: String, playlistId: String): Response

    suspend fun addTrackToPlaylist(userId: String, playlistId: String, trackInfo: TrackInfo): Response

    suspend fun removeTracksFromPlaylist(userId: String, playlistId: String, trackIds: List<String>): Response

    suspend fun getUserPlaylistTracks(userId: String, playlistId: String): Response
}

@Single
class UserPlaylistApiImpl(private val firestoreApi: FirestoreApi) : UserPlaylistApi {
    override suspend fun createPlaylist(userId: String, userName: String, playlistName: String, trackInfo: TrackInfo): Response {
        val response = firestoreApi.createPlaylist(userId, userName, playlistName, trackInfo)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun getUserPlaylists(userId: String): Response {
        val response = firestoreApi.getUserPlaylists(userId)

        return if (response is FirestoreApiResponse.Success) {
            getSuccessResponse(response.data)
        } else {
            val errorResponse = response as FirestoreApiResponse.Error
            getErrorResponse(status = errorResponse.statusCode, message = errorResponse.throwable.message.toString())
        }
    }

    override suspend fun deletePlaylist(userId: String, playlistId: String): Response {
        val response = firestoreApi.deletePlaylist(userId, playlistId)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun addTrackToPlaylist(userId: String, playlistId: String, trackInfo: TrackInfo): Response {
        val response = firestoreApi.addTrackToPlaylist(userId, playlistId, trackInfo)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun removeTracksFromPlaylist(userId: String, playlistId: String, trackIds: List<String>): Response {
        val response = firestoreApi.removeTracksFromPlaylist(userId, playlistId, trackIds)

        return if (response is FirestoreApiResponse.Error) {
            with(response) {
                getErrorResponse(status = statusCode, message = throwable.message.toString())
            }
        } else {
            getEmptyBodySuccessResponse()
        }
    }

    override suspend fun getUserPlaylistTracks(userId: String, playlistId: String): Response {
        val response = firestoreApi.getPlaylistTracks(userId, playlistId)

        return if (response is FirestoreApiResponse.Success) {
            getSuccessResponse(response.data)
        } else {
            val errorResponse = response as FirestoreApiResponse.Error
            getErrorResponse(status = errorResponse.statusCode, message = errorResponse.throwable.message.toString())
        }
    }

}