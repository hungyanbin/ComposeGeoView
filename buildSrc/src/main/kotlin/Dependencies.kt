object Dependencies {
    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val material = "com.google.android.material:material:1.3.0"

        const val composeUI = "androidx.compose.ui:ui:${Version.compose}"
        const val composeUITooling = "androidx.compose.ui:ui-tooling:${Version.compose}"
        const val composeMaterial = "androidx.compose.material:material:${Version.compose}"
        const val composeActivity = "androidx.activity:activity-compose:1.3.0-alpha03"

        const val lifecycleKts = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0"

    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0"
    }

    object Test {
        const val jUnit4 = "junit:junit:4.+"
        const val jUnit5 = "org.junit.jupiter:junit-jupiter-api:${Version.jUnit5}"
        const val jUnit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Version.jUnit5}"
    }
}

object Module {
    const val core = ":core"
    const val serializer = ":serializer"
}

object Version {
    const val kotlin = "1.4.32"
    const val jUnit5 = "5.3.1"
    const val compose = "1.0.0-beta04"

    object Android {
        const val buildTool = "30.0.3"
        const val minSdk = 23
        const val targetSdk = 30
        const val compileSdk = 30
    }
}
