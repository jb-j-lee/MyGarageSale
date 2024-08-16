// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.GRADLE}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
    }
}

plugins {
    id("com.android.application") version Versions.GRADLE apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN apply false
}