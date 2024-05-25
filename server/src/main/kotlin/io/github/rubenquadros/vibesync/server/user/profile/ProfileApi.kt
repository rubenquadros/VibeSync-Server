package io.github.rubenquadros.vibesync.server.user.profile

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.util.toFirestoreSuccessData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface ProfileApi {
    suspend fun getUserProfile(id: String): Response
}

@Single
class ProfileApiImpl(private val firestoreApi: FirestoreApi) : ProfileApi {
    @Suppress("UNCHECKED_CAST")
    override suspend fun getUserProfile(id: String): Response {
        val response = withContext(Dispatchers.IO) {
            listOf(
                async { firestoreApi.getUserProfile(id) },
                async { firestoreApi.getUserPlaylists(id) },
                async { firestoreApi.getLikedTracks(id) },
                async { firestoreApi.getLikedAlbums(id) }
            ).awaitAll()
        }

        return response.toFirestoreSuccessData { successResults ->
            getSuccessResponse(
                GetUserProfileResponse(
                    profileInfo = successResults[0] as UserProfile,
                    playlists = (successResults[1] as List<PlaylistInfo>).ifEmpty { null },
                    likedTracks = (successResults[2] as List<TrackInfo>).ifEmpty { null },
                    likedAlbums = (successResults[3] as List<MediaInfo>).ifEmpty { null }
                )
            )
        }
    }
}