plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.serialization)
}

kotlin {
    jvmToolchain(17)
}