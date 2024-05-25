package io.github.rubenquadros.vibesync.server.test.playlist

import io.github.rubenquadros.vibesync.kovibes.toImage
import io.github.rubenquadros.vibesync.kovibes.toTrackInfo
import io.github.rubenquadros.vibesync.server.model.GetPaginatedResponse
import io.github.rubenquadros.vibesync.server.model.TracksPage
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.playlist.GetPlaylistResponse
import io.github.rubenquadros.vibesync.server.playlist.PlaylistApi
import io.github.rubenquadros.vibesync.server.test.FakeApi
import io.github.rubenquadros.vibesync.test.data.geTracks
import io.github.rubenquadros.vibesync.test.data.playlist1

class FakePlaylistApi : PlaylistApi, FakeApi() {

    override suspend fun getPlaylist(id: String): Response {
        return getTestApiResponse(
            GetPlaylistResponse(
                id = playlist1.id,
                name = playlist1.name,
                images = playlist1.images.map { it.toImage() }
            )
        )
    }

    override suspend fun getPlaylistTracks(id: String, offset: Int, limit: Int): Response {
        return getTestApiResponse(
            GetPaginatedResponse(
                isNext = false,
                content = TracksPage(geTracks().map { it.toTrackInfo() })
            )
        )
    }
}