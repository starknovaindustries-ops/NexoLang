plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "dev.nexolang"
version = "0.1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("nexolang.MainKt")
}
