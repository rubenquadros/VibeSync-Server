package io.github.rubenquadros.vibesync.kovibes

import io.github.rubenquadros.kovibes.api.KoVibesApi
import io.github.rubenquadros.kovibes.api.SpotifyService
import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Single

@Single
class SpotifyApiImpl : SpotifyApi {

    private var spotifyService: SpotifyService? = null
    private val mutex = Mutex()

    override suspend fun getFeaturedPlaylists(): SpotifyApiResponse<Playlists, ErrorBody> {
        return getSpotifyService().getFeaturedPlaylists()
    }

    private suspend fun getSpotifyService(): SpotifyService {
        mutex.withLock {
            if (spotifyService == null) {
                //init kovibes
                val clientId = System.getenv("CLIENT_ID")
                val clientSecret = System.getenv("CLIENT_SECRET")
                spotifyService = KoVibesApi.createSpotifyService(clientId, clientSecret)
            }

            return spotifyService!!
        }
    }
}