import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    val coreDependencies = mapOf(
        // main
        "kotlin"                       to "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}",
        "appCompat"                    to "androidx.appcompat:appcompat:${Versions.appCompat}",
        "core"                         to "androidx.core:core-ktx:${Versions.core}",
        "loggingInterceptor"           to "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}",
        "hilt"                         to "com.google.dagger:hilt-android:${Versions.hilt}",

        "multidex"                     to "androidx.multidex:multidex:${Versions.multidex}",
        //"legacySupportV4"              to "androidx.legacy:legacy-support-v4:${Versions.legacySupportV4}",

        // PlayCore
        "playCore"                     to "com.google.android.play:core:${Versions.playCore}",

        // PlayCoreCtx
        "playCoreCtx"                  to "com.google.android.play:core:${Versions.playCoreKtx}",

        // TransactionTooLargeException detector
        "tooLargeTool"                 to "com.gu.android:toolargetool:${Versions.tooLargeTool}",

        // Work Manager
        "workManager"                  to "androidx.work:work-runtime-ktx:${Versions.workManager}",

        // google maps
        "googleMaps"                   to "com.google.android.gms:play-services-maps:${Versions.playMaps}",
        "googleServicesLocation"       to "com.google.android.gms:play-services-location:${Versions.playServiceLocation}",

        "timber"                        to "com.jakewharton.timber:timber:${Versions.timberLog}"
    )

    val coreKaptDependencies = mapOf(
        "hiltKapt"                to "com.google.dagger:hilt-compiler:${Versions.hilt}"
    )

    val mainDependencies = mapOf(
        // converter
        "gson"                     to "com.google.code.gson:gson:${Versions.gson}",

        // permission
        "permission"               to "pub.devrel:easypermissions:${Versions.permission}"
    )

    val chuckerDebugDependencies = mapOf(
        "chuckerDebug" to "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    )

    val chuckerReleaseDependencies = mapOf(
        "chuckerRelease" to "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    )

    val uiDependencies = mapOf(
        // UI
        "materialUi"      to "com.google.android.material:material:${Versions.materialUi}",
        "viewPager"       to "androidx.viewpager2:viewpager2:${Versions.viewPager}",
        "cardView"        to "androidx.cardview:cardview:${Versions.cardView}",
        "constraintLayout" to "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}",
        "glide"           to "com.github.bumptech.glide:glide:${Versions.glide}",
        "fragmentKtx"     to "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}",
        "swipeToRefresh"  to "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeToRefresh}"
    )

    val moduleDependencies = mapOf(
        // coroutine
        "coroutine"     to "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutine}",

        // lifecycle
        "lifecycle"     to "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}",

        // network
        "retrofit"      to "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "okHttp"        to "com.squareup.okhttp3:okhttp:${Versions.okHttp}",

        // utility
        "retrofitGson"  to "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}",
        "retrofitScalar" to "com.squareup.retrofit2:converter-scalars:${Versions.retrofitScalar}",
    )

    val localDbDependencies = mapOf(
        "room"      to "androidx.room:room-runtime:${Versions.room}",
        "roomKtx"   to "androidx.room:room-ktx:${Versions.room}",
        "fbstetho"  to "com.facebook.stetho:stetho:${Versions.fbStetho}"
    )

    val localDbKaptDependencies = mapOf(
        "room" to "androidx.room:room-compiler:${Versions.room}"
    )

    val localdbDebugDependencies = mapOf(
        "androiddebug" to "com.amitshekhar.android:debug-db:${Versions.androidDebug}"
    )

    val testImplementations = mapOf(
        "jUnit"          to "junit:junit:${Versions.jUnit}",
        "mockitoCore"    to "org.mockito:mockito-core:${Versions.mockitoCore}",
        "coroutineTest"  to "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}",
        "mockitoKotlin"  to "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}",
        "mockWebServer"  to "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    )

    val androidTestImplementations = mapOf(
        "coreTesting"       to "androidx.arch.core:core-testing:${Versions.coreTesting}",
        "androidJUnit"      to "androidx.test.ext:junit:${Versions.androidJUnit}",
        "androidEspresso"   to "androidx.test.espresso:espresso-core:${Versions.androidEspresso}",
        "room"              to "androidx.room:room-testing:${Versions.room}",
    )

    val chartDependencies = mapOf(
        "MPAndroidChart" to "com.github.PhilJay:MPAndroidChart:v${Versions.MPAndroidChart}"
    )
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kaptList(col: List<String>) {
    col.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementationList(col: List<String>) {
    col.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.debugImplementationList(col: List<String>) {
    col.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}

fun DependencyHandler.releaseImplementationList(col: List<String>) {
    col.forEach { dependency ->
        add("releaseImplementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementationList(col: List<String>) {
    col.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementationList(col: Collection<String>) {
    col.forEach { dependency ->
        add("testImplementation", dependency)
    }
}