object CentralDependencies {
    // AndroidX
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}" }

    // Architectural Components
    val lifecycleViewModelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycleVersion}" }
    val lifecycleLiveDataKtx by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycleVersion}" }
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycleVersion}" }

    // Coroutines
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}" }

    // Retrofit
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterGson}" }
    val okHttpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}" }

    // Navigation Components
    val navigationFragmentKtx by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }

    // Dagger Hilt
    val daggerHilt by lazy { "com.google.dagger:hilt-android:${Versions.daggerHilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hiltCompiler}" }
    val hiltCommon by lazy { "androidx.hilt:hilt-common:${Versions.hiltCommon}" }
    val hiltCompilerCommon by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCommon}" }

    // Paging 3
    val paging by lazy { "androidx.paging:paging-runtime-ktx:${Versions.paging}" }
    val pagingCompose by lazy { "androidx.paging:paging-compose:${Versions.pagingCompose}" }

    // Compose
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.composeUi}" }
    val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics:${Versions.composeUiGraphics}" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.composeUiToolingPreview}" }
    val material3 by lazy { "androidx.compose.material3:material3:${Versions.material3}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.composeMaterialVersion}" }
    val lifecycleViewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewModelCompose}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }
    val lifecycleRuntimeCompose by lazy { "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycleRuntimeCompose}" }
    val coilCompose by lazy { "io.coil-kt:coil-compose:${Versions.coilCompose}" }

    // Room
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.roomRuntime}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.roomCompiler}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.roomKtx}" }

    // Test
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val junitExt by lazy { "androidx.test.ext:junit:${Versions.junitExt}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCore}" }
    val mockitoCore by lazy { "org.mockito:mockito-core:${Versions.mockitoCore}" }
    val coreTesting by lazy { "androidx.arch.core:core-testing:${Versions.coreTesting}" }
    val coroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}" }
    val turbine by lazy { "app.cash.turbine:turbine:${Versions.turbine}" }
    val composeUiTest by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.composeUiTest}" }
    val androidxTestExtJUnit by lazy { "androidx.test.ext:junit:${Versions.androidxTestExtJUnit}" }
}

object Modules{
    const val networkStatus = ":networkStatus"
    const val api = ":api"
}