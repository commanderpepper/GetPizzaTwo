plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "commanderpepper.getpizza.permissions"
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
    implementation(project(":core:util"))
    implementation(project(":core:androidUtil"))

    //Compose
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.google.accompanist.permissions)
    implementation(libs.androidx.activity.compose)

    implementation(libs.timber)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    //Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}