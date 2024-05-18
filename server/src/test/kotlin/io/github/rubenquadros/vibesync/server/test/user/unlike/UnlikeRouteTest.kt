package io.github.rubenquadros.vibesync.server.test.user.unlike

import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.server.user.unlike.UnlikeApi
import io.ktor.client.request.delete
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class UnlikeRouteTest {

    private val mockModule = module {
        single<UnlikeApi> { FakeUnlikeApi() }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when a track is unliked successfully then a success response is received`() = testApplication {
        FakeUnlikeApi.isError = false

        val response = it.delete("user/1234/unlike-track/5678")

        response.assertOk()
    }

    @Test
    fun `when there is an error in unliking a track then an error response is received`() = testApplication {
        FakeUnlikeApi.isError = true

        val response = it.delete("user/1234/unlike-track/5678")

        response.assertError()
    }

    @Test
    fun `when an album is unliked successfully then a success response is received`() = testApplication {
        FakeUnlikeApi.isError = false

        val response = it.delete("user/1234/unlike-album/5678")

        response.assertOk()
    }

    @Test
    fun `when there is an error in unliking an album then an error response is received`() = testApplication {
        FakeUnlikeApi.isError = true

        val response = it.delete("user/1234/unlike-album/5678")

        response.assertError()
    }

    @Test
    fun `when a playlist is unliked successfully then a success response is received`() = testApplication {
        FakeUnlikeApi.isError = false

        val response = it.delete("user/1234/unlike-playlist/5678")

        response.assertOk()
    }

    @Test
    fun `when there is an error in unliking a playlist then an error response is received`() = testApplication {
        FakeUnlikeApi.isError = true

        val response = it.delete("user/1234/unlike-playlist/5678")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}