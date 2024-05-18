package io.github.rubenquadros.vibesync.kovibes

import io.github.rubenquadros.kovibes.api.response.Album
import io.github.rubenquadros.kovibes.api.response.AlbumTrack
import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.kovibes.api.response.Track
import io.github.rubenquadros.shared.models.Image
import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo

fun Album.toMediaInfo(): MediaInfo {
    return MediaInfo(
        id = id,
        name = name,
        images = images.map { it.toImage() }
    )
}

fun Artist.toMediaInfo(): MediaInfo {
    return MediaInfo(
        id = id,
        name = name,
        images = images?.map { it.toImage() }.orEmpty()
    )
}

fun Track.toTrackInfo(): TrackInfo {
    return TrackInfo(
        id = id,
        name = name,
        previewUrl = previewUrl,
        images = album.images.map { it.toImage() },
        duration = duration
    )
}

fun AlbumTrack.toTrackInfo(): TrackInfo {
    return TrackInfo(
        id = id,
        name = name,
        previewUrl = previewUrl,
        duration = duration,
        images = emptyList()
    )
}

fun io.github.rubenquadros.kovibes.api.response.Image.toImage(): Image {
    return Image(
        height = height,
        width = width,
        url = url
    )
}