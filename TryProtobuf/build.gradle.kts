plugins {
    id("java")
}

group = "com.solody.javatry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.google.protobuf:protobuf-java:4.27.2")
    implementation("com.google.protobuf:protobuf-java-util:4.27.2")
}

tasks.test {
    useJUnitPlatform()
}