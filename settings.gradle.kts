plugins {
    id("com.gradle.enterprise").version("3.1.1")
}

include(":semantic-version")
include(":semantic-version-gradle-plugin")

apply(from = java.io.File(settingsDir, "buildCacheSettings.gradle"))
