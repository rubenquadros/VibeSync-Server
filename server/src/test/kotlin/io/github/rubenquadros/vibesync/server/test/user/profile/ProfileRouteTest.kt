package io.github.rubenquadros.vibesync.server.test.user.profile

import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.server.test.assertError
import io.github.rubenquadros.vibesync.server.test.assertOk
import io.github.rubenquadros.vibesync.server.test.cleanupKoin
import io.github.rubenquadros.vibesync.server.test.setupKoin
import io.github.rubenquadros.vibesync.server.test.testApplication
import io.github.rubenquadros.vibesync.server.user.profile.ProfileApi
import io.github.rubenquadros.vibesync.test.data.userProfile
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ProfileRouteTest : KoinTest {

    private val mockModule = module {
        single<ProfileApi> { FakeProfileApi() }
    }

    @BeforeTest
    fun setup() {
        setupKoin(mockModule)
    }

    @Test
    fun `when profile data is fetched successfully then a success response is received`() = testApplication {
        FakeProfileApi.isError = false

        val response = it.get("user/234")

        response.assertOk()

        val body = response.body<UserProfile>()

        assert(body.id == userProfile.id)
    }

    @Test
    fun `when there is an error in fetching profile data from spotify then an error response is received`() = testApplication {
        FakeProfileApi.isError = true

        val response = it.get("user/234")

        response.assertError()
    }

    @AfterTest
    fun cleanup() {
        cleanupKoin(mockModule)
    }
}