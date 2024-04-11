plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "vibesync-server"
include("server")
include("firestore")
include("kovibes")
include("testUtils")
