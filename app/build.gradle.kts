plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = "com.quadrantapp"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type.
            isMinifyEnabled = true

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/license.txt")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/notice.txt")
        resources.excludes.add("META-INF/ASL2.0")
        resources.excludes.add("META-INF/*.kotlin_module")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    val dependencyGroup = listOf(
        AppDependencies.coreDependencies.values.toList(),
        AppDependencies.mainDependencies.values.toList(),
        AppDependencies.uiDependencies.values.toList(),
        AppDependencies.moduleDependencies.values.toList(),
        AppDependencies.localDbDependencies.values.toList()
    )

    val dependencyKaptGroup = listOf(
        AppDependencies.coreKaptDependencies.values.toList(),
        AppDependencies.localDbKaptDependencies.values.toList()
    )

    val dependencyDebugGroup = listOf(
        AppDependencies.chuckerDebugDependencies.values.toList()
    )

    val dependencyReleaseGroup = listOf(
        AppDependencies.chuckerReleaseDependencies.values.toList()
    )

    val dependencyTestGroup = listOf(
        AppDependencies.testImplementations.values.toList()
    )

    val dependencyAndroidTestGroup = listOf(
        AppDependencies.androidTestImplementations.values.toList()
    )

    dependencyGroup.forEach {
        implementationList(it)
    }

    dependencyKaptGroup.forEach {
        kaptList(it)
    }

    dependencyDebugGroup.forEach {
        debugImplementationList(it)
    }

    dependencyReleaseGroup.forEach {
        releaseImplementationList(it)
    }

    dependencyTestGroup.forEach {
        testImplementationList(it)
    }

    dependencyAndroidTestGroup.forEach {
        androidTestImplementationList(it)
    }

    implementation(project(":feature_price"))
}