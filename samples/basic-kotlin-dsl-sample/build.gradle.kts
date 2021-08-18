buildscript {
	repositories {
		mavenCentral()
		if (project.hasProperty("LOCAL_MAVEN_REPO")) {
			val localMavenRepo = extra.get("LOCAL_MAVEN_REPO") ?: System.getenv("LOCAL_MAVEN_REPO")
			if (localMavenRepo != null) {
				maven(url = localMavenRepo)
			}
		}

		maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
	}

	buildscript {
		dependencies {
			classpath("com.dipien:semantic-version-gradle-plugin:1.0.0-SNAPSHOT")
		}
	}
}

version = "1.0.0"

apply(plugin = "java")
apply(plugin = "com.dipien.semantic-version")
