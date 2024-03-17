// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlin) apply false
    alias(libs.plugins.googleServices) apply false
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