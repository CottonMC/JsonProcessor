import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.3.11"
}

version= "1.0.0"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    jcenter()
    mavenCentral()
    maven{
        url= URI("http://server.bbkr.space:8081/artifactory/libs-release")
    }
}

dependencies {
    compile("org.apache.bcel:bcel:6.2")
    compile(group= "io.github.cottonmc", name= "json-factory", version= "0.3.2")
    compile(project(":processor_annotations"))
}
