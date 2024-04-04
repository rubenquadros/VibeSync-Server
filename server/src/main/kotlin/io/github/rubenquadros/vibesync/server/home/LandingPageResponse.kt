package io.github.rubenquadros.vibesync.server.home

import io.github.rubenquadros.kovibes.api.response.Playlist
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LandingPageResponse(
    @SerialName("topArtists")
    val topArtists: List<TopEntity>,
    @SerialName("topAlbums")
    val topAlbums: List<TopEntity>,
    @SerialName("topTracks")
    val topTracks: List<TopEntity>,
    @SerialName("recentTracks")
    val recentTracks: List<TopEntity>? = null,
    @SerialName("featuredPlaylists")
    val featuredPlaylists: List<TopEntity>
)

fun Playlist.toTopEntity(): TopEntity {
    return TopEntity(
        id = id,
        name = name,
        image = images.firstOrNull()?.url
    )
}
