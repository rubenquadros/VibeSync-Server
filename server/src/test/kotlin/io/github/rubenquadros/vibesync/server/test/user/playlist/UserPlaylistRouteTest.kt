package io.github.rubenquadros.vibesync.server.test.user.playlist

import io.github.rubenquadros.vibesync.server.model.GetPaginatedResponse
import io.github.rubenquadros.vibesync.server.model.PlaylistPage
import io.github.rubenquadros.vibesync.server.model.TracksPage
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.server.user.playlist.NewPlaylistInfo
import io.github.rubenquadros.vibesync.server.user.playlist.UserPlaylistApi
import io.github.rubenquadros.vibesync.test.data.trackInfo
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class UserPlaylistRouteTest {

    private val fakeUserPlaylistApi = FakeUserPlaylistApi()
    private val mockModule = module {
        single<UserPlaylistApi> { fakeUserPlaylistApi }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when a playlist is created successfully then a success response is received`() = testApplication {
        fakeUserPlaylistApi.isError = false

        val response = it.post("/user/1234/create-playlist") {
            setBody(NewPlaylistInfo("Test_playlist", "Test_owner", trackInfo))
            contentType(ContentType.Application.Json)
        }

        response.assertOk()
    }

    @Test
    fun `when there is an error in creating a playlist then an error response is received`() = testApplication {
        fakeUserPlaylistApi.isError = true

        val response = it.post("/user/1234/create-playlist") {
            setBody(NewPlaylistInfo("Test_playlist", "Test_owner", trackInfo))
            contentType(ContentType.Application.Json)
        }

        response.assertError()
    }

    @Test
    fun `when a playlist is deleted successfully then a success response is received`() = testApplication {
        fakeUserPlaylistApi.isError = false

        val response = it.delete("/user/1234/playlist/1234")

        response.assertOk()
    }

    @Test
    fun `when there is an error in deleting a playlist then an error response is received`() = testApplication {
        fakeUserPlaylistApi.isError = true

        val response = it.delete("/user/1234/playlist/1234")

        response.assertError()
    }

    @Test
    fun `when a track is added successfully to a playlist then a success response is received`() = testApplication {
        fakeUserPlaylistApi.isError = false

        val response = it.patch("/user/1234/update-playlist/1234") {
            setBody(trackInfo)
            contentType(ContentType.Application.Json)
        }

        response.assertOk()
    }

    @Test
    fun `when there is an error in adding a track to a playlist then an error response is received`() = testApplication {
        fakeUserPlaylistApi.isError = true

        val response = it.patch("/user/1234/update-playlist/1234") {
            setBody(trackInfo)
            contentType(ContentType.Application.Json)
        }

        response.assertError()
    }

    @Test
    fun `when a track is successfully deleted from a playlist then a success response is received`() = testApplication {
        fakeUserPlaylistApi.isError = false

        val response = it.delete("/user/1234/update-playlist/1234") {
            setBody(listOf("1234"))
            contentType(ContentType.Application.Json)
        }

        response.assertOk()
    }

    @Test
    fun `when there is an error in deleting a track from a playlist then an error response is received`() = testApplication {
        fakeUserPlaylistApi.isError = true

        val response = it.delete("/user/1234/update-playlist/1234") {
            setBody(listOf("1234"))
            contentType(ContentType.Application.Json)
        }

        response.assertError()
    }

    @Test
    fun `when user playlists are retrieved successfully then a success response is received`() = testApplication {
        fakeUserPlaylistApi.isError = false

        val response = it.get("/user/1234/playlists?offset=0")

        response.assertOk()

        val body = response.body<GetPaginatedResponse>()

        with(body) {
            assert(!isNext)
            assert((content as PlaylistPage).items.size == 1)
        }
    }

    @Test
    fun `when there is an error in retrieving user playlists then an error response is received`() = testApplication {
        fakeUserPlaylistApi.isError = true

        val response = it.get("/user/1234/playlists?offset=0")

        response.assertError()
    }

    @Test
    fun `when a playlist tracks are retrieved successfully then a success response is received`() = testApplication {
        fakeUserPlaylistApi.isError = false

        val response = it.get("/user/1234/playlist/6789/tracks?offset=0")

        response.assertOk()

        val body = response.body<GetPaginatedResponse>()

        with(body) {
            assert(!isNext)
            assert((content as TracksPage).items.size == 1)
        }
    }

    @Test
    fun `when there is an error in fetching user playlist tracks then an error response is received`() = testApplication {
        fakeUserPlaylistApi.isError = true

        val response = it.get("/user/1234/playlist/6789/tracks?offset=0")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}