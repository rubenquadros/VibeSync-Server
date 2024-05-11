package io.github.rubenquadros.vibesync.firestore

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.firestore.model.getErrorResponse
import io.github.rubenquadros.vibesync.firestore.model.getSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.io.FileInputStream

@Single
class FirestoreApiImpl : FirestoreApi {

    private val firestore: Firestore by lazy {
        val databaseUrl = System.getenv("DATABASE_URL")

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(FileInputStream("etc/secrets/admin.json")))
            .setDatabaseUrl(databaseUrl)
            .build()

        val app = FirebaseApp.initializeApp(options)

        FirestoreClient.getFirestore(app)
    }

    override suspend fun getTopArtists(): FirestoreApiResponse<List<TopEntity>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val future = firestore.collection("top_artists").get()
                val topArtists = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                    TopEntity(
                        id =  documentSnapshot["id"].toString(),
                        name = documentSnapshot["name"].toString(),
                        image = documentSnapshot["image"].toString()
                    )
                }

                getSuccessResponse(topArtists)
            }
        }.getOrElse {
            return getErrorResponse(throwable = it)
        }
    }

    override suspend fun getTopTracks(): FirestoreApiResponse<List<TopEntity>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val future = firestore.collection("top_tracks").get()
                val topTracks = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                    TopEntity(
                        id =  documentSnapshot["id"].toString(),
                        name = documentSnapshot["name"].toString(),
                        image = documentSnapshot["image"].toString()
                    )
                }

                getSuccessResponse(topTracks)
            }
        }.getOrElse {
            return getErrorResponse(throwable = it)
        }
    }

    override suspend fun getTopAlbums(): FirestoreApiResponse<List<TopEntity>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val future = firestore.collection("top_albums").get()
                val topAlbums = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                    TopEntity(
                        id =  documentSnapshot["id"].toString(),
                        name = documentSnapshot["name"].toString(),
                        image = documentSnapshot["image"].toString()
                    )
                }

                getSuccessResponse(topAlbums)
            }
        }.getOrElse {
            return getErrorResponse(throwable = it)
        }
    }

    override suspend fun getRecentTracks(): FirestoreApiResponse<List<TopEntity>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val future = firestore.collection("recent_tracks").get()
                val recentTracks = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                    TopEntity(
                        id =  documentSnapshot["id"].toString(),
                        name = documentSnapshot["name"].toString(),
                        image = documentSnapshot["image"].toString()
                    )
                }

                getSuccessResponse(recentTracks)
            }
        }.getOrElse {
            return getErrorResponse(throwable = it)
        }
    }
}