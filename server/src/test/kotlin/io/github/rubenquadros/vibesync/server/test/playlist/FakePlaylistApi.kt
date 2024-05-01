package io.github.rubenquadros.vibesync.server.test.playlist

import io.github.rubenquadros.vibesync.server.model.GetPaginatedResponse
import io.github.rubenquadros.vibesync.server.model.PlaylistTracks
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getServerErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.model.toImage
import io.github.rubenquadros.vibesync.server.model.toTrackInfo
import io.github.rubenquadros.vibesync.server.playlist.GetPlaylistResponse
import io.github.rubenquadros.vibesync.server.playlist.PlaylistApi
import io.github.rubenquadros.vibesync.server.test.errorMessage
import io.github.rubenquadros.vibesync.test.data.geTracks
import io.github.rubenquadros.vibesync.test.data.playlist1

class FakePlaylistApi : PlaylistApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getPlaylist(id: String): Response {
        return if (isError) {
            getServerErrorResponse(errorMessage)
        } else {
            getSuccessResponse(
                data = GetPlaylistResponse(
                    id = playlist1.id,
                    name = playlist1.name,
                    images = playlist1.images.map { it.toImage() }
                )
            )
        }
    }

    override suspend fun getPlaylistTracks(id: String, offset: Int, limit: Int): Response {
        return if (isError) {
            getServerErrorResponse(errorMessage)
        } else {
            getSuccessResponse(
                data = GetPaginatedResponse(
                    isNext = false,
                    content = PlaylistTracks(geTracks().map { it.toTrackInfo() })
                )
            )
        }
    }
}