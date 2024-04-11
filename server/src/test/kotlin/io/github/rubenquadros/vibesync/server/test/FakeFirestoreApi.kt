package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.test.data.topEntity

class FakeFirestoreApi : FirestoreApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getTopArtists(): List<TopEntity> {
        return if (isError) {
            throw Exception("Error in fetching top artists.")
        } else {
            topEntity
        }
    }

    override suspend fun getTopTracks(): List<TopEntity> {
        return if (isError) {
            throw Exception("Error in fetching top tracks.")
        } else {
            topEntity
        }
    }

    override suspend fun getTopAlbums(): List<TopEntity> {
        return if (isError) {
            throw Exception("Error in fetching top albums.")
        } else {
            topEntity
        }
    }

    override suspend fun getRecentTracks(): List<TopEntity> {
        return if (isError) {
            throw Exception("Error in fetching recent tracks.")
        } else {
            topEntity
        }
    }
}