package io.github.rubenquadros.vibesync.firestore

import io.github.rubenquadros.vibesync.firestore.model.TopEntity

interface FirestoreApi {
    suspend fun getTopArtists(): List<TopEntity>

    suspend fun getTopTracks(): List<TopEntity>

    suspend fun getTopAlbums(): List<TopEntity>

    suspend fun getRecentTracks(): List<TopEntity>
}