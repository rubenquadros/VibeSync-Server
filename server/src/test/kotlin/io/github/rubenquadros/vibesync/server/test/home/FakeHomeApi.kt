package io.github.rubenquadros.vibesync.server.test.home

import io.github.rubenquadros.vibesync.server.home.HomeApi
import io.github.rubenquadros.vibesync.server.home.LandingPageResponse
import io.github.rubenquadros.vibesync.server.home.toTopEntity
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.test.FakeApi
import io.github.rubenquadros.vibesync.test.data.featuredPlaylistsResponse
import io.github.rubenquadros.vibesync.test.data.topEntity

class FakeHomeApi : HomeApi, FakeApi() {

    override suspend fun getHomePage(): Response {
        return getTestApiResponse(
            LandingPageResponse(
                topArtists = topEntity,
                topAlbums = topEntity,
                topTracks = topEntity,
                recentTracks = topEntity,
                featuredPlaylists = featuredPlaylistsResponse.items.map { it.toTopEntity() }
            )
        )
    }
}