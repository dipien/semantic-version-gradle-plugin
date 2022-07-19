buildscript {
	repositories {
		gradlePluginPortal()
	}

	buildscript {
		dependencies {
			classpath("com.dipien:semantic-version-gradle-plugin:1.0.0")
		}
	}
}

version = "1.1.0"

apply(plugin = "com.dipien.semantic-version")
