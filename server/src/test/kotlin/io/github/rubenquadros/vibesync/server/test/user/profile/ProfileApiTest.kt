package io.github.rubenquadros.vibesync.server.test.user.profile

import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.server.test.FakeFirestoreApi
import io.github.rubenquadros.vibesync.server.test.assertFirestoreFailure
import io.github.rubenquadros.vibesync.server.test.assertSuccess
import io.github.rubenquadros.vibesync.server.user.profile.GetUserProfileResponse
import io.github.rubenquadros.vibesync.server.user.profile.ProfileApiImpl
import io.github.rubenquadros.vibesync.test.data.unknownUser
import io.github.rubenquadros.vibesync.test.data.userProfile
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ProfileApiTest {

    private val fakeFirestoreApi = FakeFirestoreApi()
    private val profileAPi = ProfileApiImpl(fakeFirestoreApi)

    @Test
    fun `when user profile info is fetched successfully then a success response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = profileAPi.getUserProfile("234")

        response.assertSuccess<GetUserProfileResponse> {
            with(it.profileInfo) {
                assert(id == userProfile.id)
                assert(name == userProfile.name)
                assert(email == userProfile.email)
                assert(image == userProfile.image)
            }
        }
    }

    @Test
    fun `when there is an error in fetching user profile info then an error response is received`() = runTest {
        fakeFirestoreApi.isError = true

        val response = profileAPi.getUserProfile("234")

        response.assertFirestoreFailure()
    }

    @Test
    fun `when the user is not present then not found error response is received`() = runTest {
        fakeFirestoreApi.isError = false

        val response = profileAPi.getUserProfile(unknownUser)

        assert(response.status == HttpStatusCode.NotFound)
    }
}