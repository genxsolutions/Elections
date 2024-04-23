@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.genxsol.elections"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.genxsol.elections"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
        }
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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.5"
    }
}

dependencies {
    // modules
    implementation(project(Modules.utilities))
    implementation(project(Modules.api))

    // AndroidX
    implementation(CentralDependencies.coreKtx)
    implementation(CentralDependencies.appcompat)
    implementation(CentralDependencies.material)
    implementation(CentralDependencies.constraintLayout)
    implementation(CentralDependencies.fragmentKtx)

    // Architectural Components
    implementation(CentralDependencies.lifecycleViewModelKtx)

    // Coroutines
    implementation(CentralDependencies.coroutinesCore)
    implementation(CentralDependencies.coroutinesAndroid)

    // Retrofit
    implementation(CentralDependencies.retrofit)
    implementation(CentralDependencies.retrofitConverterGson)
    implementation(CentralDependencies.okHttpLoggingInterceptor)

    // Navigation Components
    implementation(CentralDependencies.navigationFragmentKtx)
    implementation(CentralDependencies.navigationUiKtx)

    // Dagger Hilt
    implementation(CentralDependencies.daggerHilt)
    kapt(CentralDependencies.hiltCompiler)
    implementation(CentralDependencies.hiltCommon)
    kapt(CentralDependencies.hiltCompilerCommon)

    // Paging 3
    implementation(CentralDependencies.paging)
    implementation(CentralDependencies.pagingCompose)

    // Compose
    implementation(CentralDependencies.activityCompose)
    implementation(CentralDependencies.composeBom)
    implementation(CentralDependencies.composeUi)
    implementation(CentralDependencies.composeMaterial)
    implementation(CentralDependencies.composeUiGraphics)
    implementation(CentralDependencies.composeUiToolingPreview)
    implementation(CentralDependencies.material3)
    implementation(CentralDependencies.lifecycleViewModelCompose)
    implementation(CentralDependencies.hiltNavigationCompose)
    implementation(CentralDependencies.navigationCompose)
    implementation(CentralDependencies.lifecycleRuntimeCompose)
    implementation(CentralDependencies.coilCompose)

    //Room
    implementation(CentralDependencies.roomRuntime)
    kapt(CentralDependencies.roomCompiler)
    implementation(CentralDependencies.roomKtx)

    // Unit Test
    testImplementation(CentralDependencies.junit)
    testImplementation(CentralDependencies.composeUiTest)
    testImplementation(CentralDependencies.mockitoCore)
    testImplementation(CentralDependencies.coreTesting)
    testImplementation(CentralDependencies.coroutinesTest)
    testImplementation(CentralDependencies.turbine)

    // UI Test
    androidTestImplementation(CentralDependencies.junitExt)
    androidTestImplementation(CentralDependencies.espressoCore)
    androidTestImplementation(CentralDependencies.androidxTestExtJUnit)
    androidTestImplementation(CentralDependencies.composeUiTest)
    
    // debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


}