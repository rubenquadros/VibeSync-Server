package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.firestore.model.getErrorResponse
import io.github.rubenquadros.vibesync.test.data.firestoreErrorResponse
import io.github.rubenquadros.vibesync.test.data.firestoreSuccessNoBodyResponse
import io.github.rubenquadros.vibesync.test.data.likedAlbumsResponse
import io.github.rubenquadros.vibesync.test.data.likedTracksResponse
import io.github.rubenquadros.vibesync.test.data.topEntityResponse
import io.github.rubenquadros.vibesync.test.data.unknownUser
import io.github.rubenquadros.vibesync.test.data.userPlaylistsResponse
import io.github.rubenquadros.vibesync.test.data.userProfileResponse
import io.ktor.http.HttpStatusCode

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

    override suspend fun getUserProfile(id: String): FirestoreApiResponse<UserProfile> {
        return when  {
            isError -> firestoreErrorResponse
            id == unknownUser -> getErrorResponse(statusCode = HttpStatusCode.NotFound, throwable = Exception("Unknown"))
            else -> userProfileResponse
        }
    }

    override suspend fun likeTrack(userId: String, trackInfo: TrackInfo): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun unlikeTrack(userId: String, trackId: String): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun unlikeAlbum(userId: String, albumId: String): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun unlikePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun getLikedTracks(userId: String): FirestoreApiResponse<List<TrackInfo>> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            likedTracksResponse
        }
    }

    override suspend fun getLikedAlbums(userId: String): FirestoreApiResponse<List<MediaInfo>> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            likedAlbumsResponse
        }
    }

    override suspend fun createPlaylist(userId: String, userName: String, playlistName: String, trackInfo: TrackInfo): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun getUserPlaylists(userId: String): FirestoreApiResponse<List<PlaylistInfo>> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            userPlaylistsResponse
        }
    }

    override suspend fun addTrackToPlaylist(
        userId: String,
        playlistId: String,
        trackInfo: TrackInfo
    ): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun removeTracksFromPlaylist(userId: String, playlistId: String, trackIds: List<String>): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }

    override suspend fun getPlaylistTracks(userId: String, playlistId: String): FirestoreApiResponse<List<TrackInfo>> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            likedTracksResponse
        }
    }

    override suspend fun deletePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            firestoreSuccessNoBodyResponse
        }
    }
}