package io.github.rubenquadros.vibesync.firestore

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.io.FileInputStream

@Single
class FirestoreApiImpl : FirestoreApi {

    private var firestore: Firestore? = null
    private val mutex = Mutex()

    override suspend fun getTopArtists(): List<TopEntity> {
        return withContext(Dispatchers.IO) {
            val future = getFirestore().collection("top_artists").get()
            future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TopEntity(
                    id =  documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    image = documentSnapshot["image"].toString()
                )
            }
        }
    }

    override suspend fun getTopTracks(): List<TopEntity> {
        return withContext(Dispatchers.IO) {
            val future = getFirestore().collection("top_tracks").get()
            future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TopEntity(
                    id =  documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    image = documentSnapshot["image"].toString()
                )
            }
        }
    }

    override suspend fun getTopAlbums(): List<TopEntity> {
        return withContext(Dispatchers.IO) {
            val future = getFirestore().collection("top_albums").get()
            future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TopEntity(
                    id =  documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    image = documentSnapshot["image"].toString()
                )
            }
        }
    }

    override suspend fun getRecentTracks(): List<TopEntity> {
        return withContext(Dispatchers.IO) {
            val future = getFirestore().collection("recent_tracks").get()
            future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TopEntity(
                    id =  documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    image = documentSnapshot["image"].toString()
                )
            }
        }
    }

    private suspend fun getFirestore(): Firestore {
        mutex.withLock {
            if (firestore == null) {
                //init firestore
                val databaseUrl = System.getenv("DATABASE_URL")

                val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(FileInputStream("etc/secrets/admin.json")))
                    .setDatabaseUrl(databaseUrl)
                    .build()

                val app = FirebaseApp.initializeApp(options)

                firestore = FirestoreClient.getFirestore(app)
            }
            return firestore!!
        }
    }
}