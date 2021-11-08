@file:Suppress("UnstableApiUsage")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "PexWallpapers"

include(

    ":app",

    ":core",
    ":base",
    ":domain",
    ":composables",
    ":test-shared",

    ":pex",
    ":pex:data",
    ":pex:usecases",

    ":feature_home",
//    ":feature_search",
//    ":feature_favorites",
//    ":feature_settings"
)