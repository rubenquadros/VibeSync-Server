package io.github.rubenquadros.vibesync.server.test.home

import io.github.rubenquadros.vibesync.server.home.HomeApi
import io.github.rubenquadros.vibesync.server.home.LandingPageResponse
import io.github.rubenquadros.vibesync.server.home.toTopEntity
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getServerErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.test.errorMessage
import io.github.rubenquadros.vibesync.test.data.featuredPlaylistsResponse
import io.github.rubenquadros.vibesync.test.data.topEntity

class FakeHomeApi : HomeApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getHomePage(): Response {
        return if (isError) {
            getServerErrorResponse(errorMessage)
        } else {
            getSuccessResponse(
                data = LandingPageResponse(
                    topArtists = topEntity,
                    topAlbums = topEntity,
                    topTracks = topEntity,
                    recentTracks = topEntity,
                    featuredPlaylists = featuredPlaylistsResponse.result.items.map { it.toTopEntity() }
                )
            )
        }
    }
}