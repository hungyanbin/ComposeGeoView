buildscript {

    val kotlin_version = "1.4.32"

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:7.0.0-alpha14")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    }
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
//
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

