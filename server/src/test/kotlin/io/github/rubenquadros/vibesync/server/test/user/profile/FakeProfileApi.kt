package io.github.rubenquadros.vibesync.server.test.user.profile

import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.test.FakeApi
import io.github.rubenquadros.vibesync.server.user.profile.ProfileApi
import io.github.rubenquadros.vibesync.test.data.userProfile

class FakeProfileApi : ProfileApi, FakeApi() {

    override suspend fun getUserProfile(id: String): Response {
        return getTestApiResponse(userProfile)
    }
}