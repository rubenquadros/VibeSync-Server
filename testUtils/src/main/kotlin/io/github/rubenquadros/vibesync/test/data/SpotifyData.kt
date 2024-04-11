package io.github.rubenquadros.vibesync.test.data

import io.github.rubenquadros.kovibes.api.response.Error
import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlist
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse

val errorResponse = SpotifyApiResponse.Error(
    body = ErrorBody(
        error = Error(
            status = 500, message = "Something went wrong."
        )
    )
)

val featuredPlaylistsResponse = SpotifyApiResponse.Success(
    result = Playlists(
        isNext = false,
        items = getPlaylists()
    )
)

private fun getPlaylists(): List<Playlist> = listOf(
    Playlist(
        collaborative = false,
        description = "D1",
        id = "123",
        name = "Playlist1",
        images = emptyList(),
        public = true
    ),
    Playlist(
        collaborative = false,
        description = "D2",
        id = "456",
        name = "Playlist2",
        images = emptyList(),
        public = true
    )
)