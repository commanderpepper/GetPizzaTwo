plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "commanderpepper.getpizza.map"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":model"))
    implementation(project(":data:local"))
    implementation(project(":core:util"))
    implementation(project(":core:androidUtil"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation)

    implementation(libs.timber)

    //Compose
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.google.accompanist.permissions)

    //Google Maps
    implementation(libs.google.maps)

    //Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}