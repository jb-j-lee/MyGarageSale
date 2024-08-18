object Sdk {
    const val COMPILE = 34
    const val MIN = 24
    const val TARGET = 34
}

object Versions {
    const val GRADLE = "8.5.2"

    const val KOTLIN = "1.9.22"
    const val COROUTINE = "1.7.3"

    const val DATASTORE = "1.1.1"
    const val LIFECYCLE = "2.8.4"
    const val NAVIGATION = "2.7.7"
    const val ROOM = "2.6.1"

    const val HILT = "2.52"

    const val MOSHI = "1.15.1"
    const val OKHTTP3 = "4.12.0"
    const val RETROFIT = "2.11.0"

    const val GLIDE = "4.16.0"
}

@Suppress("unused")
object AndroidX {
    const val PREFERENCE = "androidx.preference:preference-ktx:1.2.1"

    const val SECURITY = "androidx.security:security-crypto-ktx:1.1.0-alpha06"
    const val SWIPEREFRESH = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    object DATASTORE {
        const val DATASTORE = "androidx.datastore:datastore:${Versions.DATASTORE}"
        const val PREFERENCES = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
    }
}

@Suppress("unused")
object AAC {
    object Lifecycle {
        const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
        const val VIEWMODEL_SAVEDSTATE =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE}"
    }

    object Navigation {
        const val FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
        const val UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    }

    object Room {
        const val KTX = "androidx.room:room-ktx:${Versions.ROOM}"
        const val RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
        const val COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
        const val TESTING = "androidx.room:room-testing:${Versions.ROOM}"
    }
}

@Suppress("unused")
object Google {
    const val FLEXBOX = "com.google.android.flexbox:flexbox:3.0.0"
    const val GSON = "com.google.code.gson:gson:2.10.1"
    const val MATERIAL = "com.google.android.material:material:1.12.0"

    object Dagger {
        const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
        const val HILT_NAVIGATION = "androidx.hilt:hilt-navigation-fragment:1.2.0"
    }
}

@Suppress("unused")
object JetBrain {
    const val ANNOTATIONS = "org.jetbrains:annotations:23.0.0"

    object COROUTINE {
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"
        const val TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINE}"
    }
}

@Suppress("unused")
object SquareUp {
    object OKHTTP3 {
        const val BOM = "com.squareup.okhttp3:okhttp-bom:${Versions.OKHTTP3}"

        const val OKHTTP = "com.squareup.okhttp3:okhttp"
        const val URLCONNECTION = "com.squareup.okhttp3:okhttp-urlconnection"
        const val LOGGING = "com.squareup.okhttp3:logging-interceptor"
        const val MOCKWEBSERVER = "com.squareup.okhttp3:mockwebserver"
    }

    object RETROFIT2 {
        const val BOM = "com.squareup.retrofit2:retrofit-bom:${Versions.RETROFIT}"

        const val RETROFIT = "com.squareup.retrofit2:retrofit"
        const val MOSHI = "com.squareup.retrofit2:converter-moshi"
        const val GSON = "com.squareup.retrofit2:converter-gson"
        const val SCALARS = "com.squareup.retrofit2:converter-scalars"
    }

    object MOSHI {
        const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
        const val CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
    }
}

@Suppress("unused")
object Jsoup {
    const val JSOUP = "org.jsoup:jsoup:1.18.1"
}

@Suppress("unused")
object Zxing {
    const val JOURNEYAPPS = "com.journeyapps:zxing-android-embedded:4.3.0"
    const val GOOGLE = "com.google.zxing:core:3.5.3"
}

@Suppress("unused")
object Glide {
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
}