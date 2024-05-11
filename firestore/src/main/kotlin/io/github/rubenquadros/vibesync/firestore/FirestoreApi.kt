package io.github.rubenquadros.vibesync.firestore

import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.TopEntity

interface FirestoreApi {
    suspend fun getTopArtists(): FirestoreApiResponse<List<TopEntity>>

    suspend fun getTopTracks(): FirestoreApiResponse<List<TopEntity>>

    suspend fun getTopAlbums(): FirestoreApiResponse<List<TopEntity>>

    suspend fun getRecentTracks(): FirestoreApiResponse<List<TopEntity>>
}