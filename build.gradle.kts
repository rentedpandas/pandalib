import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
    `maven-publish`
    signing
}

group = "com.github.rentedpandas"
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

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

java {
    withSourcesJar()
    withJavadocJar()
}

    publishing {

    publications {
        create<MavenPublication>(project.name) {

            from(components["java"])

            this.groupId = project.group.toString()
            this.artifactId = project.name.toLowerCase()
            this.version = project.version.toString()

            pom {

                name.set(project.name)
                description.set(project.description)

                developers {
                    developer {
                        name.set("rentedpandas")
                    }
                }

                url.set("https://github.com/rentedpandas/pandalib")

                scm {
                    connection.set("scm:git:git://github.com/rentedpandas/pandalib.git")
                    url.set("https://github.com/rentedpandas/pandalib}/tree/master")
                }

            }

        }
    }

}

signing {
    sign(publishing.publications)
}