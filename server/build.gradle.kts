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
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
    arg("KOIN_DEFAULT_MODULE","false")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("io.github.rubenquadros.vibesync.server.ApplicationKt")
}

tasks.withType<ShadowJar>() {
    mergeServiceFiles()
}