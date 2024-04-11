import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

group = "io.github.rubenquadros"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.ktor)

    implementation(libs.bundles.koin)
    implementation(libs.koin.annotation)
    ksp(libs.koin.ksp)

    implementation(libs.coroutines.jvm)

    implementation(project(":firestore"))
    implementation(project(":kovibes"))

    testImplementation(project(":testUtils"))
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
    arg("KOIN_DEFAULT_MODULE","false")
}

tasks.test {
    useJUnitPlatform()
    environment(
        environmentVariables = arrayOf(
            Pair("CLIENT_ID", "TestClientId"),
            Pair("CLIENT_SECRET", "TestClientSecret")
        )
    )
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("io.github.rubenquadros.vibesync.server.ApplicationKt")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

configurations {
    testImplementation.get().exclude("org.jetbrains.kotlin", "kotlin-test-junit")
}