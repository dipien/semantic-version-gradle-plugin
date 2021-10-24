plugins {
    id("com.gradle.enterprise").version("3.7.1")
}

include(":semantic-version")
include(":semantic-version-gradle-plugin")
include(":semantic-version-android-gradle-plugin")

apply(from = java.io.File(settingsDir, "buildCacheSettings.gradle"))
