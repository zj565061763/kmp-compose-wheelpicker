pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    jcenter()
    mavenLocal()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "kmp-compose-wheelpicker"
include(":composeApp")
include(":lib")
