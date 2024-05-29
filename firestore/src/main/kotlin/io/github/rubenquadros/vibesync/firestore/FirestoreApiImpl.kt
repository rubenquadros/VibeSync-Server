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
import io.github.rubenquadros.vibesync.firestore.model.LikedAlbumsPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.TracksPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.firestore.model.UserPlaylistsPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.firestore.model.getErrorResponse
import io.github.rubenquadros.vibesync.firestore.model.getSuccessResponse
import io.github.rubenquadros.vibesync.firestore.model.getSuccessWithoutBody
import io.github.rubenquadros.vibesync.firestore.model.toUpdateObject
import io.github.rubenquadros.vibesync.firestore.model.toWriteObject
import io.ktor.http.HttpStatusCode
import org.koin.core.annotation.Single
import java.io.FileInputStream
import java.util.UUID

@Single
class FirestoreApiImpl : FirestoreApi {

    private val pager: Pager by lazy { Pager() }

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
            val topArtists =
                firestore.collection("top_artists").await().map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                    TopEntity(
                        id = queryDocumentSnapshot["id"].toString(),
                        name = queryDocumentSnapshot["name"].toString(),
                        image = queryDocumentSnapshot["image"].toString()
                    )
                }

            getSuccessResponse(topArtists)
        }
    }

    override suspend fun getTopTracks(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse {
            val topTracks =
                firestore.collection("top_tracks").await().map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                    TopEntity(
                        id = queryDocumentSnapshot["id"].toString(),
                        name = queryDocumentSnapshot["name"].toString(),
                        image = queryDocumentSnapshot["image"].toString()
                    )
                }

            getSuccessResponse(topTracks)
        }
    }

    override suspend fun getTopAlbums(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse {
            val topAlbums =
                firestore.collection("top_albums").await().map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                    TopEntity(
                        id = queryDocumentSnapshot["id"].toString(),
                        name = queryDocumentSnapshot["name"].toString(),
                        image = queryDocumentSnapshot["image"].toString()
                    )
                }

            getSuccessResponse(topAlbums)
        }
    }

    override suspend fun getRecentTracks(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse {
            val recentTracks =
                firestore.collection("recent_tracks").await().map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                    TopEntity(
                        id = queryDocumentSnapshot["id"].toString(),
                        name = queryDocumentSnapshot["name"].toString(),
                        image = queryDocumentSnapshot["image"].toString()
                    )
                }

            getSuccessResponse(recentTracks)
        }
    }

    override suspend fun getUserProfile(id: String): FirestoreApiResponse<UserProfile> {
        return getFirestoreResponse {
            firestore.collection("users").document(id).await()?.let {
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
            val future = firestore.collection("users").document(userId).collection("liked_tracks").document(trackInfo.id)
                .set(trackInfo.toWriteObject())

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun unlikeTrack(userId: String, trackId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("liked_tracks").document(trackId)
                .update("status", "N")

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("liked_albums").document(mediaInfo.id)
                .set(mediaInfo.toWriteObject())

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun unlikeAlbum(userId: String, albumId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("liked_albums").document(albumId)
                .update("status", "N")

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("playlists").document(mediaInfo.id)
                .set(mediaInfo.toWriteObject())

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun unlikePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("playlists").document(playlistId)
                .update("status", "N")

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun getLikedTracks(userId: String): FirestoreApiResponse<List<TrackInfo>> {
        return getFirestoreResponse {
            val query = firestore.collection("users").document(userId).collection("liked_tracks")
                .whereEqualTo("status", "Y")
                .orderBy("timestamp", Query.Direction.DESCENDING).limit(10)

            val likedTracks = query.await().map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                val data = queryDocumentSnapshot.getDataMap()
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

    override suspend fun getPaginatedLikedTracks(
        userId: String,
        offset: Int
    ): FirestoreApiResponse<TracksPaginatedResponse> {
        return getFirestoreResponse {
            val query = firestore.collection("users").document(userId).collection("liked_tracks")
                .whereEqualTo("status", "Y")
                .orderBy("timestamp", Query.Direction.DESCENDING)

            val pageResponse = pager.getPage(offset.coerceAtLeast(0), query)

            getSuccessResponse(
                TracksPaginatedResponse(
                    isNext = pageResponse.isNext,
                    items = pageResponse.page.map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                        val data = queryDocumentSnapshot.getDataMap()
                        TrackInfo(
                            id = data["id"].toString(),
                            name = data["name"].toString(),
                            duration = data["duration"] as Long,
                            previewUrl = (data["previewUrl"] as? String),
                            images = data.getImages()
                        )
                    }
                )
            )

        }
    }

    override suspend fun getLikedAlbums(userId: String): FirestoreApiResponse<List<MediaInfo>> {
        return getFirestoreResponse {
            val query = firestore.collection("users").document(userId).collection("liked_albums")
                .whereEqualTo("status", "Y")
                .orderBy("timestamp", Query.Direction.DESCENDING).limit(10)

            val likedAlbums = query.await().map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                val data = queryDocumentSnapshot.getDataMap()
                MediaInfo(
                    id = data["id"].toString(),
                    name = data["name"].toString(),
                    images = data.getImages()
                )
            }

            getSuccessResponse(likedAlbums)
        }
    }

    override suspend fun getPaginatedLikedAlbums(
        userId: String,
        offset: Int
    ): FirestoreApiResponse<LikedAlbumsPaginatedResponse> {
        return getFirestoreResponse {
            val query = firestore.collection("users").document(userId).collection("liked_albums")
                .whereEqualTo("status", "Y")
                .orderBy("timestamp", Query.Direction.DESCENDING)

            val pageResponse = pager.getPage(offset.coerceAtLeast(0), query)

            getSuccessResponse(
                LikedAlbumsPaginatedResponse(
                    isNext = pageResponse.isNext,
                    items = pageResponse.page.map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                        val data = queryDocumentSnapshot.getDataMap()
                        MediaInfo(
                            id = data["id"].toString(),
                            name = data["name"].toString(),
                            images = data.getImages()
                        )
                    }
                )
            )
        }
    }

    override suspend fun createPlaylist(
        userId: String,
        userName: String,
        playlistName: String,
        trackInfo: TrackInfo
    ): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val batch = firestore.batch()

            val playlistId = UUID.randomUUID().toString()
            val playlistInfo = PlaylistInfo(playlistId, playlistName, userName, trackInfo.images.firstOrNull()?.url)

            val userDocument =
                firestore.collection("users").document(userId).collection("playlists").document(playlistId)
            val playlistDocument = firestore.collection("playlists").document(playlistId)
            val tracksDocument = playlistDocument.collection("tracks").document(trackInfo.id)

            batch.set(userDocument, playlistInfo.toUpdateObject())
            batch.set(playlistDocument, playlistInfo)
            batch.set(tracksDocument, trackInfo.toWriteObject())

            val future = batch.commit()

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun getUserPlaylists(userId: String): FirestoreApiResponse<List<PlaylistInfo>> {
        return getFirestoreResponse {
            val query = firestore.collection("users").document(userId).collection("playlists")
                .whereEqualTo("status", "Y")
                .orderBy("updatedAt", Query.Direction.DESCENDING).limit(10)

            val playlists = query.await().map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                val data = queryDocumentSnapshot.getDataMap()
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

    override suspend fun getPaginatedUserPlaylists(
        userId: String,
        offset: Int
    ): FirestoreApiResponse<UserPlaylistsPaginatedResponse> {
        return getFirestoreResponse {
            val query = firestore.collection("users").document(userId).collection("playlists")
                .whereEqualTo("status", "Y")
                .orderBy("updatedAt", Query.Direction.DESCENDING)

            val pageResponse = pager.getPage(offset.coerceAtLeast(0), query)

            getSuccessResponse(
                UserPlaylistsPaginatedResponse(
                    isNext = pageResponse.isNext,
                    items = pageResponse.page.map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                        val data = queryDocumentSnapshot.getDataMap()
                        PlaylistInfo(
                            id = data["id"].toString(),
                            playlistName = data["playlistName"].toString(),
                            owner = data["owner"].toString(),
                            image = (data["image"] as? String)
                        )
                    }
                )
            )
        }
    }

    override suspend fun addTrackToPlaylist(
        userId: String,
        playlistId: String,
        trackInfo: TrackInfo
    ): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val batch = firestore.batch()

            val userDocument =
                firestore.collection("users").document(userId).collection("playlists").document(playlistId)
            val tracksDocument =
                firestore.collection("playlists").document(playlistId).collection("tracks").document(trackInfo.id)

            batch.update(userDocument, mapOf("updatedAt" to FieldValue.serverTimestamp()))
            batch.set(tracksDocument, trackInfo.toWriteObject())

            val future = batch.commit()

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun removeTracksFromPlaylist(
        userId: String,
        playlistId: String,
        trackIds: List<String>
    ): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val batch = firestore.batch()

            val userDocument =
                firestore.collection("users").document(userId).collection("playlists").document(playlistId)
            val playlistTracksCollection = firestore.collection("playlists").document(playlistId).collection("tracks")

            batch.update(userDocument, mapOf("updatedAt" to FieldValue.serverTimestamp()))

            trackIds.forEach { id ->
                batch.update(playlistTracksCollection.document(id), mapOf("status" to "N"))
            }

            val future = batch.commit()

            future.await()

            getSuccessWithoutBody()
        }
    }

    override suspend fun getPlaylistTracks(
        userId: String,
        playlistId: String,
        offset: Int
    ): FirestoreApiResponse<TracksPaginatedResponse> {
        return getFirestoreResponse {
            val query = firestore.collection("playlists").document(playlistId).collection("tracks")
                .whereEqualTo("status", "Y")

            val pageResponse = pager.getPage(offset.coerceAtLeast(0), query)

            getSuccessResponse(
                TracksPaginatedResponse(
                    isNext = pageResponse.isNext,
                    items = pageResponse.page.map { queryDocumentSnapshot: QueryDocumentSnapshot ->
                        val data = queryDocumentSnapshot.getDataMap()
                        TrackInfo(
                            id = data["id"].toString(),
                            name = data["name"].toString(),
                            duration = data["duration"] as Long,
                            previewUrl = (data["previewUrl"] as? String),
                            images = data.getImages()
                        )
                    }
                )
            )
        }
    }

    override suspend fun deletePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreResponse {
            val future = firestore.collection("users").document(userId).collection("playlists").document(playlistId)
                .update("status", "N")

            future.await()

            getSuccessWithoutBody()
        }
    }
}