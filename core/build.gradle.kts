import java.util.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

val properties = File(rootDir, "local.properties").inputStream().use {
    Properties().apply { load(it) }
}

android {
    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_API_URL", properties.getProperty("BASE_API_URL"))
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val dependencyGroup = listOf(
        AppDependencies.coreDependencies.values.toList(),
        AppDependencies.mainDependencies.values.toList(),
        AppDependencies.moduleDependencies.values.toList()
    )

    val dependencyKaptGroup = listOf(
        AppDependencies.coreKaptDependencies.values.toList()
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
}