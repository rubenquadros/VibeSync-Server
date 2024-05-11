package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.test.data.firestoreErrorResponse
import io.github.rubenquadros.vibesync.test.data.topEntityResponse

class FakeFirestoreApi : FirestoreApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getTopArtists(): FirestoreApiResponse<List<TopEntity>> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            topEntityResponse
        }
    }

    override suspend fun getTopTracks(): FirestoreApiResponse<List<TopEntity>> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            topEntityResponse
        }
    }

    override suspend fun getTopAlbums(): FirestoreApiResponse<List<TopEntity>> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            topEntityResponse
        }
    }

    override suspend fun getRecentTracks(): FirestoreApiResponse<List<TopEntity>> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            topEntityResponse
        }
    }
}