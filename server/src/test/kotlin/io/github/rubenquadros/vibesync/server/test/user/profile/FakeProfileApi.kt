package io.github.rubenquadros.vibesync.server.test.user.profile

import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.server.test.apiErrorResponse
import io.github.rubenquadros.vibesync.server.user.profile.ProfileApi
import io.github.rubenquadros.vibesync.test.data.userProfile

class FakeProfileApi : ProfileApi {

    companion object {
        var isError: Boolean = false
    }

    override suspend fun getUserProfile(id: String): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getSuccessResponse(userProfile)
        }
    }
}