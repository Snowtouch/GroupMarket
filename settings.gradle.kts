pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GroupMarket"
include(":app")
include(":core")
include(":feature_home")
include(":feature_account")
include(":feature_auth")
include(":feature_groups")
include(":feature_advertisement_details")
include(":feature_new_advertisement")
include(":feature_messages")
