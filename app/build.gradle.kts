plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //Dagger
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.dagger.hilt.plugin)
    //Parcelable y serializable
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.kotlin.parcelize)
    //Firebase service
    alias(libs.plugins.gms.google.services)
}

android {
    namespace = "com.example.ordernow"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ordernow"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //Splashscreen
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.storage)
    // Dagger-Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.hilt.compiler)
    //Serializable y Parcelable
    implementation(libs.kotlinx.serialization.json)
    //navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.navigation.compose)
    //Lottie animacion
    implementation(libs.lottie.compose)
    //FirebaseBoom + Auth
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    //Firebase Realtime Database
    implementation(libs.firebase.database.ktx)
    //Coil
    implementation(libs.coil.compose)
    //constraintlayout
    implementation(libs.androidx.constraintlayout.compose)
    //Material 3 icons
    implementation(libs.compose.material.icons.extended)

    //Coroutines
    //implementation(libs.androidx.lifecycle.viewmodel.compose)
    //implementation(libs.androidx.lifecycle.runtime.compose)
    //implementation(libs.kotlinx.coroutines.core)
    //Test unitarios
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //testImplementation(kotlin("test"))
}