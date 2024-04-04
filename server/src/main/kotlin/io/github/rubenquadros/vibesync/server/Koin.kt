package io.github.rubenquadros.vibesync.server

import io.github.rubenquadros.vibesync.firestore.FirestoreModule
import io.github.rubenquadros.vibesync.kovibes.SpotifyModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(listOf(KoinServerModule().module))
    }
}

@Module(includes = [FirestoreModule::class, SpotifyModule::class])
@ComponentScan
class KoinServerModule