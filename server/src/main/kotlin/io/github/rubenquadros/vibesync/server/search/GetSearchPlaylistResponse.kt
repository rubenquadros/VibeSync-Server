package io.github.rubenquadros.vibesync.server.search

import io.github.rubenquadros.vibesync.server.playlist.GetPlaylistResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchPlaylistResponse(
    @SerialName("isNext")
    val isNext: Boolean,
    @SerialName("playlists")
    val playlists: List<GetPlaylistResponse>
)
