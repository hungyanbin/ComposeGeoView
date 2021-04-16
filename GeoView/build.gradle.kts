plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = Version.Android.compileSdk
    buildToolsVersion = Version.Android.buildTool

    defaultConfig {
        minSdk = Version.Android.minSdk
        targetSdk = Version.Android.targetSdk
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures.compose = true
}

dependencies {
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.Android.composeUI)
    implementation(Dependencies.Android.composeMaterial)
    implementation(Dependencies.Android.composeUITooling)
    testImplementation(Dependencies.Test.jUnit4)

    implementation(project(Module.core))
    implementation(project(Module.serializer))
}