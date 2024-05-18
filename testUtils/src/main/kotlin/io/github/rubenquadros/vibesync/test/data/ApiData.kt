package io.github.rubenquadros.vibesync.test.data

import io.github.rubenquadros.shared.models.Image
import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo

val trackInfo = TrackInfo(
    id = "1234",
    name = "test_track",
    duration = 1000,
    previewUrl = "https://test_preview.mp4",
    images = listOf(Image(height = 100, width = 100, url = "https://test.png"))
)

val mediaInfo = MediaInfo(
    id = "1234",
    name = "test_media",
    images = listOf(Image(height = 100, width = 100, url = "https://test_media.png"))
)