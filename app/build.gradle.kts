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

    val keystoreFile = project.rootProject.file("api.properties")
    val properties = Properties()
    properties.load(keystoreFile.inputStream())

    defaultConfig {
        applicationId = "commanderpepper.getpizza"
        minSdk = 26
        targetSdk = 34
        versionCode = 10
        versionName = "3.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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

    signingConfigs {
        create("release"){
            keyAlias = properties.getProperty("ALIAS_NAME")
            keyPassword = properties.getProperty("ALIAS_PW")
            storeFile = file("./android.jks")
            storePassword = properties.getProperty("JKS_PW")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
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
    implementation(project(":core:util"))
    implementation(project(":core:androidUtil"))
    implementation(project(":feature:map"))
    implementation(project(":feature:permissions"))
    implementation(project(":feature:favorites"))
    implementation(project(":data:local"))
    implementation(project(":data:remote"))
    implementation(project(":model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.location)
    implementation(libs.androidx.navigation)
    implementation(libs.timber)

    //Compose
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.activity.compose)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}