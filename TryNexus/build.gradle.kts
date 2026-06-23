plugins {
    id("java")
    id("maven-publish")
}

group = "com.solody"
//version = "1.0-SNAPSHOT"
version = "1.4-RELEASE"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.solody"
            artifactId = "trynexus"
            version = project.version.toString()

            from(components["java"])
        }
    }
    repositories {
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            val releasesRepoUrl = uri("http://localhost:8081/repository/maven-releases/")
            val snapshotsRepoUrl = uri("http://localhost:8081/repository/maven-snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            isAllowInsecureProtocol = true

            credentials {
                username = "kent"

                password = "12345678"
            }
        }
    }
}