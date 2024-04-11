package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.test.data.errorResponse
import io.github.rubenquadros.vibesync.test.data.featuredPlaylistsResponse

class FakeSpotifyApi : SpotifyApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getFeaturedPlaylists(): SpotifyApiResponse<Playlists, ErrorBody> {
        return if (isError) {
            errorResponse
        } else {
            featuredPlaylistsResponse
        }
    }
}