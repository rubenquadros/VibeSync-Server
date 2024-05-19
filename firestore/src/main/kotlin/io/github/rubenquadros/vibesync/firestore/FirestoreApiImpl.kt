package io.github.rubenquadros.vibesync.firestore

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.FieldValue
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.Query
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.firestore.model.getErrorResponse
import io.github.rubenquadros.vibesync.firestore.model.getSuccessResponse
import io.github.rubenquadros.vibesync.firestore.model.getSuccessWithoutBody
import io.github.rubenquadros.vibesync.firestore.model.toUpdateObject
import io.github.rubenquadros.vibesync.firestore.model.toWriteObject
import io.ktor.http.HttpStatusCode
import org.koin.core.annotation.Single
import java.io.FileInputStream
import java.util.*

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
        return getFirestoreResponse {
            val future = firestore.collection("top_artists").get()
            val topArtists = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TopEntity(
                    id = documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    image = documentSnapshot["image"].toString()
                )
            }

            getSuccessResponse(topArtists)
        }
    }

    override suspend fun getTopTracks(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse {
            val future = firestore.collection("top_tracks").get()
            val topTracks = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TopEntity(
                    id = documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    image = documentSnapshot["image"].toString()
                )
            }

            getSuccessResponse(topTracks)
        }
    }

    override suspend fun getTopAlbums(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse {
            val future = firestore.collection("top_albums").get()
            val topAlbums = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TopEntity(
                    id = documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    image = documentSnapshot["image"].toString()
                )
            }

            getSuccessResponse(topAlbums)
        }
    }

    override suspend fun getRecentTracks(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse {
            val future = firestore.collection("recent_tracks").get()
            val recentTracks = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TopEntity(
                    id = documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    image = documentSnapshot["image"].toString()
                )
            }

            getSuccessResponse(recentTracks)
        }
    }

    override suspend fun getUserProfile(id: String): FirestoreApiResponse<UserProfile> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(id).get()
            future.get().data?.let {
                getSuccessResponse(
                    UserProfile(
                        id = it["id"].toString(),
                        name = it["name"].toString(),
                        email = it["email"].toString(),
                        image = it["image"]?.toString()
                    )
                )
            } ?: getErrorResponse(
                statusCode = HttpStatusCode.NotFound,
                throwable = Exception("User not found.")
            )
        }
    }

    override suspend fun likeTrack(userId: String, trackInfo: TrackInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeData {
                firestore.collection("users").document(userId).collection("liked_tracks").document(trackInfo.id)
                    .set(trackInfo.toWriteObject())
            }

            getSuccessWithoutBody()
        }
    }

    override suspend fun unlikeTrack(userId: String, trackId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeData {
                firestore.collection("users").document(userId).collection("liked_tracks").document(trackId)
                    .delete()
            }

            getSuccessWithoutBody()
        }
    }

    override suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeData {
                firestore.collection("users").document(userId).collection("liked_albums").document(mediaInfo.id)
                    .set(mediaInfo.toWriteObject())
            }

            getSuccessWithoutBody()
        }
    }

    override suspend fun unlikeAlbum(userId: String, albumId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeData {
                firestore.collection("users").document(userId).collection("liked_albums").document(albumId)
                    .delete()
            }
            getSuccessWithoutBody()
        }
    }

    override suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeData {
                firestore.collection("users").document(userId).collection("playlists").document(mediaInfo.id)
                    .set(mediaInfo.toWriteObject())
            }

            getSuccessWithoutBody()
        }
    }

    override suspend fun unlikePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeData {
                firestore.collection("users").document(userId).collection("playlists").document(playlistId)
                    .delete()
            }

            getSuccessWithoutBody()
        }
    }

    override suspend fun getLikedTracks(userId: String): FirestoreApiResponse<List<TrackInfo>> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("liked_tracks")
                .orderBy("timestamp", Query.Direction.DESCENDING).get()

            val likedTracks = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                val data = documentSnapshot.getDataMap()
                TrackInfo(
                    id = data["id"].toString(),
                    name = data["name"].toString(),
                    duration = data["duration"] as Long,
                    previewUrl = (data["previewUrl"] as? String),
                    images = data.getImages()
                )
            }

            getSuccessResponse(likedTracks)
        }
    }

    override suspend fun getLikedAlbums(userId: String): FirestoreApiResponse<List<MediaInfo>> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("liked_albums")
                .orderBy("timestamp", Query.Direction.DESCENDING).get()

            val likedAlbums = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                val data = documentSnapshot.getDataMap()
                MediaInfo(
                    id = data["id"].toString(),
                    name = data["name"].toString(),
                    images = data.getImages()
                )
            }

            getSuccessResponse(likedAlbums)
        }
    }

    override suspend fun createPlaylist(
        userId: String,
        userName: String,
        playlistName: String,
        trackInfo: TrackInfo
    ): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeBatch {
                val batch = firestore.batch()

                val playlistId = UUID.randomUUID().toString()
                val playlistInfo = PlaylistInfo(playlistId, playlistName, userName, trackInfo.images.firstOrNull()?.url)

                val userDocument =
                    firestore.collection("users").document(userId).collection("playlists").document(playlistId)
                val playlistDocument = firestore.collection("playlists").document(playlistId)
                val tracksDocument = playlistDocument.collection("tracks").document(trackInfo.id)

                batch.set(userDocument, playlistInfo.toUpdateObject())
                batch.set(playlistDocument, playlistInfo)
                batch.set(tracksDocument, trackInfo)

                batch.commit()
            }

            getSuccessWithoutBody()
        }
    }

    override suspend fun getUserPlaylists(userId: String): FirestoreApiResponse<List<PlaylistInfo>> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("playlists")
                .orderBy("updatedAt", Query.Direction.DESCENDING).get()

            val playlists = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                val data = documentSnapshot.getDataMap()
                PlaylistInfo(
                    id = data["id"].toString(),
                    playlistName = data["playlistName"].toString(),
                    owner = data["owner"].toString(),
                    image = (data["image"] as? String)
                )
            }

            getSuccessResponse(playlists)
        }
    }

    override suspend fun addTrackToPlaylist(
        userId: String,
        playlistId: String,
        trackInfo: TrackInfo
    ): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeBatch {
                val batch = firestore.batch()

                val userDocument = firestore.collection("users").document(userId).collection("playlists").document(playlistId)
                val tracksDocument = firestore.collection("playlists").document(playlistId).collection("tracks").document(trackInfo.id)

                batch.update(userDocument, mapOf("updatedAt" to FieldValue.serverTimestamp()))
                batch.set(tracksDocument, trackInfo)

                batch.commit()
            }

            getSuccessWithoutBody()
        }
    }

    override suspend fun removeTracksFromPlaylist(userId: String, playlistId: String, trackIds: List<String>): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeBatch {
                val batch = firestore.batch()

                val userDocument = firestore.collection("users").document(userId).collection("playlists").document(playlistId)
                val playlistDocument = firestore.collection("playlists").document(playlistId)

                batch.update(userDocument, mapOf("updatedAt" to FieldValue.serverTimestamp()))

                trackIds.forEach { id ->
                    batch.delete(playlistDocument.collection("tracks").document(id))
                }

                batch.commit()
            }

            getSuccessWithoutBody()
        }
    }

    override suspend fun getPlaylistTracks(userId: String, playlistId: String): FirestoreApiResponse<List<TrackInfo>> {
        return getFirestoreResponse {
            val future = firestore.collection("playlists").document(playlistId).collection("tracks").get()

            val tracks = future.get().documents.map { documentSnapshot: QueryDocumentSnapshot ->
                TrackInfo(
                    id = documentSnapshot["id"].toString(),
                    name = documentSnapshot["name"].toString(),
                    duration = documentSnapshot["duration"] as Long,
                    images = documentSnapshot.getImages()
                )
            }

            getSuccessResponse(tracks)
        }
    }

    override suspend fun deletePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            writeBatch {
                val batch = firestore.batch()

                val userDocument = firestore.collection("users").document(userId).collection("playlists").document(playlistId)
                val playlistDocument = firestore.collection("playlists").document(playlistId)

                batch.delete(userDocument)

                val future = playlistDocument.collection("tracks").get()
                future.get().documents.forEach { queryDocumentSnapshot: QueryDocumentSnapshot ->
                    batch.delete(queryDocumentSnapshot.reference)
                }

                batch.delete(playlistDocument)

                batch.commit()
            }
            getSuccessWithoutBody()
        }
    }
}