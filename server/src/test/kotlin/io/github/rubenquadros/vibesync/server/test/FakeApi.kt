package io.github.rubenquadros.vibesync.server.test

import io.github.rubenquadros.vibesync.server.model.Response
import io.github.rubenquadros.vibesync.server.model.getEmptyBodySuccessResponse
import io.github.rubenquadros.vibesync.server.model.getSuccessResponse
import io.github.rubenquadros.vibesync.test.TestApi

abstract class FakeApi: TestApi() {

    fun getTestApiResponse(response: Any): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getSuccessResponse(response)
        }
    }

    fun getEmptyBodyTestApiResponse(): Response {
        return if (isError) {
            apiErrorResponse
        } else {
            getEmptyBodySuccessResponse()
        }
    }
}