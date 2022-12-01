plugins {
    id("com.gradle.enterprise").version("3.11.4")
}

if (System.getenv("CI") == "true") {
    buildCache {
        local {
            directory = File(System.getProperty("user.home"), "/gradle-build-cache")
        }
    }
}

include(":semantic-version")
include(":semantic-version-gradle-plugin")
include(":semantic-version-android-gradle-plugin")
