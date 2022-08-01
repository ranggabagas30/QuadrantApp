plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val retrofitDependencyGroup = listOf(
        AppDependencies.moduleDependencies["retrofit"],
        AppDependencies.moduleDependencies["retrofitGson"]
    )

    val dependencyGroup = listOf(
        retrofitDependencyGroup,
        AppDependencies.testImplementations.values.toList()
    )

    val dependencyAndroidTestGroup = listOf(
        AppDependencies.androidTestImplementations.values.toList()
    )

    dependencyGroup.forEach {
        it.forEach { dep ->
            dep?.also {
                implementation(it)
            }
        }
    }

    dependencyAndroidTestGroup.forEach {
        androidTestImplementationList(it)
    }
}