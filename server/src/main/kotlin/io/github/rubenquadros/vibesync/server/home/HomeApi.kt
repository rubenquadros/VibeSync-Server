package io.github.rubenquadros.vibesync.server.home

import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.vibesync.firestore.FirestoreApi
import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.kovibes.SpotifyApi
import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getErrorResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.util.toFirestoreSuccessData
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
        val response = withContext(Dispatchers.IO) {
            listOf(
                async { firestoreApi.getTopArtists() },
                async { firestoreApi.getTopAlbums() },
                async { firestoreApi.getTopTracks() },
                async { firestoreApi.getRecentTracks() },
                async { spotifyApi.getFeaturedPlaylists() }
            )
        }.awaitAll()

        val featuredPlaylistsResponse: SpotifyApiResponse<Playlists, ErrorBody> = response[4] as SpotifyApiResponse<Playlists, ErrorBody>

        return if (featuredPlaylistsResponse is SpotifyApiResponse.Error) {
            getErrorResponse(featuredPlaylistsResponse.body)
        } else {
            listOf(
                response[0] as FirestoreApiResponse<List<TopEntity>>,
                response[1] as FirestoreApiResponse<List<TopEntity>>,
                response[2] as FirestoreApiResponse<List<TopEntity>>,
                response[3] as FirestoreApiResponse<List<TopEntity>>
            ).toFirestoreSuccessData { successResults ->
                val recentTracksResponse = successResults[3] as List<TopEntity>
                val featuredPlaylists = (featuredPlaylistsResponse as SpotifyApiResponse.Success).result.items

                getSuccessResponse(
                    data = LandingPageResponse(
                        topArtists = successResults[0] as List<TopEntity>,
                        topAlbums = successResults[1] as List<TopEntity>,
                        topTracks = successResults[2] as List<TopEntity>,
                        recentTracks = recentTracksResponse.ifEmpty { null },
                        featuredPlaylists = featuredPlaylists.take(10).map { it.toTopEntity() }
                    )
                )
            }
        }

    }
}