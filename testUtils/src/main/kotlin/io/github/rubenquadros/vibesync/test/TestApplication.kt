package io.github.rubenquadros.vibesync.test

import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication

fun startTestApplication(block: suspend (builder: ApplicationTestBuilder) -> Unit) {
    testApplication {
        block(this)
    }
}