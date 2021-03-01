import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
}

group = "io.github.rentedpandas"
version = "1.0"
description = "Kotlin-based library for Minecraft plugin development. (Using Spigot API)"

repositories {
    jcenter()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test-junit"))

    compileOnly("org.spigotmc", "spigot", "1.16.5-R0.1-SNAPSHOT")
    testCompileOnly("org.spigotmc", "spigot", "1.16.5-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}