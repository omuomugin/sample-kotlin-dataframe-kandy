plugins {
    kotlin("jvm") version "2.0.0"

    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "sample"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://packages.jetbrains.team/maven/p/kds/kotlin-ds-maven")
}

dependencies {
    implementation("org.jetbrains.kotlinx:dataframe:0.13.1")
    implementation("org.jetbrains.kotlinx:kotlin-statistics-jvm:0.2.1")

    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.6.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("start") {
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("sample.Main")
    workingDir = projectDir
}
