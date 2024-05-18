package io.github.rubenquadros.vibesync.server.user.profile

import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import org.koin.core.annotation.Single

interface ProfileApi {
    suspend fun getUserProfile(id: String): Response
}

@Single
class ProfileApiImpl(private val firestoreApi: FirestoreApi) : ProfileApi {
    override suspend fun getUserProfile(id: String): Response {
        val response = firestoreApi.getUserProfile(id)

        return if (response is FirestoreApiResponse.Success) {
            getSuccessResponse(response.data)
        } else {
            val errorResponse = (response as FirestoreApiResponse.Error)
            getErrorResponse(
                status = errorResponse.statusCode,
                message = errorResponse.throwable.message.toString()
            )
        }
    }
}