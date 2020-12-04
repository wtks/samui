import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.4.20"
    kotlin("plugin.serialization") version "1.4.10"
}

group = "work.wtks.samui"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClassName = "work.wtks.samui.Main"
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile(group = "com.1stleg", name = "jnativehook", version = "2.1.0")
    compile(group = "io.nats", name = "jnats", version = "2.8.0")
    compile("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "work.wtks.samui.Main"
    }

    from(
        configurations.compile.get().map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}
