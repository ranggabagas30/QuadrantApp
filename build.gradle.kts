// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
    }
}

plugins {
    id("com.android.application").version(Versions.androidGradlePlugin).apply(false)
    id("com.android.library").version(Versions.androidGradlePlugin).apply(false)
    id("org.jetbrains.kotlin.android").version(Versions.kotlin).apply(false)
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}