package io.github.rubenquadros.vibesync.kovibes

import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse

interface SpotifyApi {
    suspend fun getFeaturedPlaylists(): SpotifyApiResponse<Playlists, ErrorBody>
}