package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.LikedAlbumsPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.TracksPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.firestore.model.UserPlaylistsPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.firestore.model.getErrorResponse
import io.github.rubenquadros.vibesync.test.TestApi
import io.github.rubenquadros.vibesync.test.data.firestoreErrorResponse
import io.github.rubenquadros.vibesync.test.data.likedAlbums
import io.github.rubenquadros.vibesync.test.data.topEntity
import io.github.rubenquadros.vibesync.test.data.tracks
import io.github.rubenquadros.vibesync.test.data.unknownUser
import io.github.rubenquadros.vibesync.test.data.userPlaylists
import io.github.rubenquadros.vibesync.test.data.userProfileResponse
import io.ktor.http.HttpStatusCode

abstract class TestFirestoreApi : TestApi(), FirestoreApi {
    fun <R>getFirestoreResponse(
        response: R
    ): FirestoreApiResponse<R> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            FirestoreApiResponse.Success(response)
        }
    }

    fun getFirestoreNoBodyResponse(): FirestoreApiResponse<Nothing> {
        return if (isError) {
            firestoreErrorResponse
        } else {
            FirestoreApiResponse.SuccessNoBody
        }
    }
}

class FakeFirestoreApi : TestFirestoreApi() {

    override suspend fun getTopArtists(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse(topEntity)
    }

    override suspend fun getTopTracks(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse(topEntity)
    }

    override suspend fun getTopAlbums(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse(topEntity)
    }

    override suspend fun getRecentTracks(): FirestoreApiResponse<List<TopEntity>> {
        return getFirestoreResponse(topEntity)
    }

    override suspend fun getUserProfile(id: String): FirestoreApiResponse<UserProfile> {
        return when  {
            isError -> firestoreErrorResponse
            id == unknownUser -> getErrorResponse(statusCode = HttpStatusCode.NotFound, throwable = Exception("Unknown"))
            else -> userProfileResponse
        }
    }

    override suspend fun likeTrack(userId: String, trackInfo: TrackInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun unlikeTrack(userId: String, trackId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun unlikeAlbum(userId: String, albumId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun unlikePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun getLikedTracks(userId: String): FirestoreApiResponse<List<TrackInfo>> {
        return getFirestoreResponse(tracks)
    }

    override suspend fun getPaginatedLikedTracks(userId: String, offset: Int): FirestoreApiResponse<TracksPaginatedResponse> {
        return getFirestoreResponse(TracksPaginatedResponse(isNext = false, items = tracks))
    }

    override suspend fun getLikedAlbums(userId: String): FirestoreApiResponse<List<MediaInfo>> {
        return getFirestoreResponse(likedAlbums)
    }

    override suspend fun getPaginatedLikedAlbums(
        userId: String,
        offset: Int
    ): FirestoreApiResponse<LikedAlbumsPaginatedResponse> {
        return getFirestoreResponse(LikedAlbumsPaginatedResponse(isNext = false, items = likedAlbums))
    }

    override suspend fun createPlaylist(userId: String, userName: String, playlistName: String, trackInfo: TrackInfo): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun getUserPlaylists(userId: String): FirestoreApiResponse<List<PlaylistInfo>> {
        return getFirestoreResponse(userPlaylists)
    }

    override suspend fun getPaginatedUserPlaylists(
        userId: String,
        offset: Int
    ): FirestoreApiResponse<UserPlaylistsPaginatedResponse> {
        return getFirestoreResponse(UserPlaylistsPaginatedResponse(isNext = false, items = userPlaylists))
    }

    override suspend fun addTrackToPlaylist(
        userId: String,
        playlistId: String,
        trackInfo: TrackInfo
    ): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun removeTracksFromPlaylist(userId: String, playlistId: String, trackIds: List<String>): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }

    override suspend fun getPlaylistTracks(userId: String, playlistId: String, offset: Int): FirestoreApiResponse<TracksPaginatedResponse> {
        return getFirestoreResponse(TracksPaginatedResponse(isNext = false, items = tracks))
    }

    override suspend fun deletePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing> {
        return getFirestoreNoBodyResponse()
    }
}