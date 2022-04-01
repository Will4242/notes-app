import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.6.10"
    jacoco
    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    application
}

group = "me.will"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    // report is always generated after tests run
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    testImplementation("org.slf4j:slf4j-simple:2.0.0-alpha6")
    // For Streaming to XML and JSON
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
// https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml/2.13.2

    // For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.6.10")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "MainKt"
}
