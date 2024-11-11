pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "GetPizzaTwo"
include(":app")
include(":feature:map")
include(":feature:favorites")
include(":data:local")
include(":data:remote")
include(":core:ui")
include(":core:util")
include(":model")
include(":core:androidUtil")
include(":feature:permissions")
