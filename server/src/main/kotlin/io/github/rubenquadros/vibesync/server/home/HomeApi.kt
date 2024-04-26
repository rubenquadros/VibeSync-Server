package io.github.rubenquadros.vibesync.server.home

import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.server.model.Error
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface HomeApi {
    suspend fun getHomePage(): Response
}

@Single
class HomeApiImpl(
    private val firestoreApi: FirestoreApi,
    private val spotifyApi: SpotifyApi
) : HomeApi {

    @Suppress("UNCHECKED_CAST")
    override suspend fun getHomePage(): Response {
        val response = runCatching {
            withContext(Dispatchers.IO) {
                listOf(
                    async { firestoreApi.getTopArtists() },
                    async { firestoreApi.getTopAlbums() },
                    async { firestoreApi.getTopTracks() },
                    async { firestoreApi.getRecentTracks() },
                    async { spotifyApi.getFeaturedPlaylists() }
                )
            }.awaitAll()
        }.getOrElse {
            return getErrorResponse(message = it.message.toString())
        }

        val featuredPlaylistsResponse: SpotifyApiResponse<Playlists, ErrorBody> = response[4] as SpotifyApiResponse<Playlists, ErrorBody>

        return if (featuredPlaylistsResponse is SpotifyApiResponse.Error) {
            val error = featuredPlaylistsResponse.body.error
            Response(
                status = HttpStatusCode(error.status, error.message),
                data = Error(message = error.message)
            )
        } else {
            val recentTracksResponse = response[3] as List<TopEntity>
            val featuredPlaylists = (featuredPlaylistsResponse as SpotifyApiResponse.Success).result.items

            Response(
                status = HttpStatusCode.OK,
                data = LandingPageResponse(
                    topArtists = response[0] as List<TopEntity>,
                    topAlbums = response[1] as List<TopEntity>,
                    topTracks = response[2] as List<TopEntity>,
                    recentTracks = recentTracksResponse.ifEmpty { null },
                    featuredPlaylists = featuredPlaylists.take(10).map { it.toTopEntity() }
                )
            )
        }

    }

}