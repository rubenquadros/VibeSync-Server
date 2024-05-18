package io.github.rubenquadros.vibesync.server.test.user.profile

import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.server.test.FakeFirestoreApi
import io.github.rubenquadros.vibesync.server.test.assertFirestoreFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import io.github.rubenquadros.vibesync.server.user.profile.ProfileApiImpl
import io.github.rubenquadros.vibesync.test.data.unknownUser
import io.github.rubenquadros.vibesync.test.data.userProfile
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ProfileApiTest {

    private val profileAPi = ProfileApiImpl(FakeFirestoreApi())

    @Test
    fun `when user profile info is fetched successfully then a success response is received`() = runTest {
        FakeFirestoreApi.isError = false

        val response = profileAPi.getUserProfile("234")

        response.assertSuccess<UserProfile> {
            with(it) {
                assert(id == userProfile.id)
                assert(name == userProfile.name)
                assert(email == userProfile.email)
                assert(image == userProfile.image)
            }
        }
    }

    @Test
    fun `when there is an error in fetching user profile info then an error response is received`() = runTest {
        FakeFirestoreApi.isError = true

        val response = profileAPi.getUserProfile("234")

        response.assertFirestoreFailure()
    }

    @Test
    fun `when the user is not present then not found error response is received`() = runTest {
        FakeFirestoreApi.isError = false

        val response = profileAPi.getUserProfile(unknownUser)

        assert(response.status == HttpStatusCode.NotFound)
    }
}