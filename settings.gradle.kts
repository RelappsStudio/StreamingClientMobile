
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
        maven { url = uri("https://storage.googleapis.com/download.flutter.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    val storageUrl: String = System.getenv("FLUTTER_STORAGE_BASE_URL") ?: "https://storage.googleapis.com"
    repositories {
        google()
        mavenCentral()
        maven("$storageUrl/download.flutter.io")
    }
}

rootProject.name = "Local Streaming"
include(":app")

val flutterModulePath = File(settingsDir, "flutter_module")

val flutterSettings = File(flutterModulePath, "/.android/include_flutter.groovy")
if (flutterSettings.exists()) {
    apply(from = flutterSettings)
} else {
    throw GradleException("Flutter module not found at: ${flutterSettings.absolutePath}")
}