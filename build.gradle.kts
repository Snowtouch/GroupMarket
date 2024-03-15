// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("org.sonarqube") version "4.4.0.3356"
}
sonar {
    properties {
        property("sonar.projectKey", "GroupMarket")
        property("sonar.projectName", "GroupMarket")
        property("sonar.organization", "")
        property("sonar.host.url", "http://localhost:9000")
    }
}