package io.github.rubenquadros.vibesync.firestore

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.LikedAlbumsPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.TracksPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.firestore.model.UserPlaylistsPaginatedResponse
import io.github.rubenquadros.vibesync.firestore.model.UserProfile

interface FirestoreApi {
    suspend fun getTopArtists(): FirestoreApiResponse<List<TopEntity>>

    suspend fun getTopTracks(): FirestoreApiResponse<List<TopEntity>>

    suspend fun getTopAlbums(): FirestoreApiResponse<List<TopEntity>>

    suspend fun getRecentTracks(): FirestoreApiResponse<List<TopEntity>>

    suspend fun getUserProfile(id: String): FirestoreApiResponse<UserProfile>

    suspend fun likeTrack(userId: String, trackInfo: TrackInfo): FirestoreApiResponse<Nothing>

    suspend fun unlikeTrack(userId: String, trackId: String): FirestoreApiResponse<Nothing>

    suspend fun likeAlbum(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing>

    suspend fun unlikeAlbum(userId: String, albumId: String): FirestoreApiResponse<Nothing>

    suspend fun likePlaylist(userId: String, mediaInfo: MediaInfo): FirestoreApiResponse<Nothing>

    suspend fun unlikePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing>

    suspend fun getLikedTracks(userId: String): FirestoreApiResponse<List<TrackInfo>>

    suspend fun getPaginatedLikedTracks(userId: String, offset: Int): FirestoreApiResponse<TracksPaginatedResponse>

    suspend fun getLikedAlbums(userId: String): FirestoreApiResponse<List<MediaInfo>>

    suspend fun getPaginatedLikedAlbums(userId: String, offset: Int): FirestoreApiResponse<LikedAlbumsPaginatedResponse>

    suspend fun createPlaylist(
        userId: String,
        userName: String,
        playlistName: String,
        trackInfo: TrackInfo
    ): FirestoreApiResponse<Nothing>

    suspend fun getUserPlaylists(userId: String): FirestoreApiResponse<List<PlaylistInfo>>

    suspend fun getPaginatedUserPlaylists(
        userId: String,
        offset: Int
    ): FirestoreApiResponse<UserPlaylistsPaginatedResponse>

    suspend fun addTrackToPlaylist(
        userId: String,
        playlistId: String,
        trackInfo: TrackInfo
    ): FirestoreApiResponse<Nothing>

    suspend fun removeTracksFromPlaylist(
        userId: String,
        playlistId: String,
        trackIds: List<String>
    ): FirestoreApiResponse<Nothing>

    suspend fun getPlaylistTracks(userId: String, playlistId: String, offset: Int): FirestoreApiResponse<TracksPaginatedResponse>

    suspend fun deletePlaylist(userId: String, playlistId: String): FirestoreApiResponse<Nothing>
}