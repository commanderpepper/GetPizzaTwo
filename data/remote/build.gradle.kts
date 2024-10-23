plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(project(":model"))

    implementation(libs.kotlinx.serialization)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotinx.serialization)
    implementation(libs.okhttp.interceptor)

    implementation(libs.koin.core)
}