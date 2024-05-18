plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.koin.annotation)
    ksp(libs.koin.ksp)

    implementation(libs.coroutines.jvm)

    implementation(libs.ktor.serialization)

    api(libs.kovibes.jvm)

    api(project(":shared"))
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