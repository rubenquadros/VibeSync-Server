package io.github.rubenquadros.vibesync.server.test.user.like

import io.github.rubenquadros.shared.models.MediaInfo
import io.github.rubenquadros.shared.models.TrackInfo
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.server.user.like.LikeApi
import io.github.rubenquadros.vibesync.test.data.mediaInfo
import io.github.rubenquadros.vibesync.test.data.trackInfo
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class LikeRouteTest {

    private val fakeLikeApi = FakeLikeApi()
    private val mockModule = module {
        single<LikeApi> { fakeLikeApi }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when a track is liked successfully then a success response is received`() = testApplication {
        fakeLikeApi.isError = false

        val response = it.post("user/1234/like-track") {
            setBody(trackInfo)
            contentType(ContentType.Application.Json)
        }

        response.assertOk()
    }

    @Test
    fun `when there is an error in liking a track then an error response is received`() = testApplication {
        fakeLikeApi.isError = true

        val response = it.post("user/1234/like-track") {
            setBody(trackInfo)
            contentType(ContentType.Application.Json)
        }

        response.assertError()
    }

    @Test
    fun `when an album is liked successfully then a success response is received`() = testApplication {
        fakeLikeApi.isError = false

        val response = it.post("user/1234/like-album") {
            setBody(mediaInfo)
            contentType(ContentType.Application.Json)
        }

        response.assertOk()
    }

    @Test
    fun `when there is an error in liking an album then an error response is received`() = testApplication {
        fakeLikeApi.isError = true

        val response = it.post("user/1234/like-album") {
            setBody(mediaInfo)
            contentType(ContentType.Application.Json)
        }

        response.assertError()
    }

    @Test
    fun `when a playlist is liked successfully then a success response is received`() = testApplication {
        fakeLikeApi.isError = false

        val response = it.post("user/1234/like-playlist") {
            setBody(mediaInfo)
            contentType(ContentType.Application.Json)
        }

        response.assertOk()
    }

    @Test
    fun `when there is an error in liking a playlist then an error response is received`() = testApplication {
        fakeLikeApi.isError = true

        val response = it.post("user/1234/like-playlist") {
            setBody(mediaInfo)
            contentType(ContentType.Application.Json)
        }

        response.assertError()
    }

    @Test
    fun `when user liked tracks are fetched successfully then a success response is received`() = testApplication {
        fakeLikeApi.isError = false

        val response = it.get("user/1234/liked-tracks")

        response.assertOk()

        val body = response.body<List<TrackInfo>>()

        assert(body.size == 1)
    }

    @Test
    fun `when there is an error in fetching user liked tracks then an error response is received`() = testApplication {
        fakeLikeApi.isError = true

        val response = it.get("user/1234/liked-tracks")

        response.assertError()
    }

    @Test
    fun `when user liked albums are fetched successfully then a success response is received`() = testApplication {
        fakeLikeApi.isError = false

        val response = it.get("user/1234/liked-albums")

        response.assertOk()

        val body = response.body<List<MediaInfo>>()

        assert(body.size == 1)
    }

    @Test
    fun `when there is an error in fetching user liked albums then an error response is received`() = testApplication {
        fakeLikeApi.isError = true

        val response = it.get("user/1234/liked-albums")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}