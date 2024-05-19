package io.github.rubenquadros.vibesync.server.test.album

import io.github.rubenquadros.vibesync.kovibes.toImage
import io.github.rubenquadros.vibesync.kovibes.toTrackInfo
import io.github.rubenquadros.vibesync.server.album.AlbumApi
import io.github.rubenquadros.vibesync.server.album.GetAlbumResponse
import io.github.rubenquadros.vibesync.server.album.GetAlbumTracksResponse
import io.github.rubenquadros.vibesync.server.artist.toArtistInfo
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.test.FakeApi
import io.github.rubenquadros.vibesync.test.data.album1
import io.github.rubenquadros.vibesync.test.data.geTracks

class FakeAlbumApi : AlbumApi, FakeApi() {

    override suspend fun getAlbum(id: String): Response {
        return getTestApiResponse(
            response = with(album1) {
                GetAlbumResponse(
                    albumType = albumType,
                    id = this.id,
                    name = name,
                    releaseDate = releaseDate,
                    images = images.map { it.toImage() },
                    artists = artists.map { it.toArtistInfo() }
                )
            }
        )
    }

    override suspend fun getAlbumTracks(id: String, offset: Int, limit: Int): Response {
        return getTestApiResponse(
            GetAlbumTracksResponse(
                isNext = false,
                tracks = geTracks().map { it.toTrackInfo() }
            )
        )
    }
}