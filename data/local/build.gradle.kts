import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "commanderpepper.getpizza.local"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keystoreFile = project.rootProject.file("api.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val clientID = properties.getProperty("FOURSQUARE_CLIENT_ID") ?: ""
        buildConfigField(
            type = "String",
            name = "FOURSQUARE_CLIENT_ID",
            value = clientID
        )

        val clientSecret = properties.getProperty("FOURSQUARE_CLIENT_SECRET")
        buildConfigField(
            type = "String",
            name = "FOURSQUARE_CLIENT_SECRET",
            value = clientSecret
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":model"))
    implementation(project(":data:remote"))
    implementation(project(":core:util"))

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.coroutines.test)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.junit)
}