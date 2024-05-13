@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.genxsol.networkStatus"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // AndroidX
    implementation(CentralDependencies.coreKtx)
    implementation(CentralDependencies.appcompat)
    implementation(CentralDependencies.material)

    // Dagger Hilt
    implementation(CentralDependencies.daggerHilt)
    kapt(CentralDependencies.hiltCompiler)
    implementation(CentralDependencies.hiltCommon)
    kapt(CentralDependencies.hiltCompilerCommon)

    // Test
    testImplementation(CentralDependencies.junit)
    androidTestImplementation(CentralDependencies.espressoCore)

}