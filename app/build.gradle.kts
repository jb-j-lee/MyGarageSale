plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.myjb.dev.mygaragesale"
    compileSdk = Sdk.COMPILE

    defaultConfig {
        minSdk = Sdk.MIN
        targetSdk = Sdk.TARGET
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
    lint {
        abortOnError = true
    }
}

dependencies {
    implementation("androidx.activity:activity-ktx:1.9.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.fragment:fragment-ktx:1.8.2")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // LifeCycle
    implementation(AAC.Lifecycle.LIVEDATA)
    implementation(AAC.Lifecycle.VIEWMODEL)

    // Hilt
    implementation(Google.Dagger.HILT)
    kapt(Google.Dagger.HILT_COMPILER)

    implementation(Google.MATERIAL)

    // Jsoup
    implementation(Jsoup.JSOUP)

    // Zxing
    implementation(Zxing.GOOGLE)
    implementation(Zxing.JOURNEYAPPS)

    // Glide
    implementation(Glide.GLIDE)
    kapt(Glide.COMPILER)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}