package io.github.rubenquadros.vibesync.server.test.user.playlist

import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.test.FakeApi
import io.github.rubenquadros.vibesync.server.user.playlist.UserPlaylistApi
import io.github.rubenquadros.vibesync.test.data.tracks
import io.github.rubenquadros.vibesync.test.data.userPlaylists

class FakeUserPlaylistApi : UserPlaylistApi, FakeApi() {

    override suspend fun createPlaylist(
        userId: String,
        userName: String,
        playlistName: String,
        trackInfo: TrackInfo
    ): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun getUserPlaylists(userId: String): Response {
        return getTestApiResponse(userPlaylists)
    }

    override suspend fun deletePlaylist(userId: String, playlistId: String): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun addTrackToPlaylist(userId: String, playlistId: String, trackInfo: TrackInfo): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun removeTracksFromPlaylist(
        userId: String,
        playlistId: String,
        trackIds: List<String>
    ): Response {
        return getEmptyBodyTestApiResponse()
    }

    override suspend fun getUserPlaylistTracks(userId: String, playlistId: String): Response {
        return getTestApiResponse(tracks)
    }
}