plugins {
    id("java-library")
    id("java")
    id("kotlin")
    id ("kotlinx-serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation (Dependencies.Kotlin.stdlib)
    implementation(Dependencies.Kotlin.serializationJson)
    implementation(project(Module.core))

    testImplementation(Dependencies.Test.jUnit5)
    testRuntimeOnly(Dependencies.Test.jUnit5Engine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
