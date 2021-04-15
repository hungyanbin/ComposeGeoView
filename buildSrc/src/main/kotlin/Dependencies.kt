object Dependencies {
    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val material = "com.google.android.material:material:1.3.0"

        const val composeUI = "androidx.compose.ui:ui:1.0.0-beta04"
        const val composeUITooling = "androidx.compose.ui:ui-tooling:1.0.0-beta04"
        const val composeMaterial = "androidx.compose.material:material:1.0.0-beta04"

    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:1.4.32"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0"
    }

    object Test {
        const val jUnit4 = "junit:junit:4.+"
        const val jUnit5 = "org.junit.jupiter:junit-jupiter-api:5.3.1"
        const val jUnit5Engine = "org.junit.jupiter:junit-jupiter-engine:5.3.1"
    }
}