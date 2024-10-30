import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "commanderpepper.getpizza"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
        compose = true
    }

    defaultConfig {
        applicationId = "commanderpepper.getpizza"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
            name = "FOURSQUARE_CLIENT_ID",
            value = clientSecret
        )

        val googleApiKey = properties.getProperty("GOOGLE_MAPS_API") ?: ""
        manifestPlaceholders["GOOGLE_MAP_KEY"] = googleApiKey
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}