[![Dipien](https://raw.githubusercontent.com/dipien/dipien-component-builder/master/.github/dipien_logo.png)](http://www.dipien.com)

# Semantic Version Gradle Plugin

## Features

## Setup

Add the following configuration to your root `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/dipien/semantic-version-gradle-plugin/releases/latest)

### Using the plugins DSL

For android app projects:
```groovy
plugins {
  id "com.dipien.semanticversion.android" version "X.Y.Z"
}
```

For non-android app projects:

```groovy
plugins {
  id "com.dipien.semanticversion" version "X.Y.Z"
}
```

### Using legacy plugin application

For android app projects:

```groovy
buildscript {
    repositories {
        mavenCentral() // or gradlePluginPortal()
    }
    dependencies {
        classpath("com.dipien:semantic-version-android-gradle-plugin:X.Y.Z")
    }
}
    
apply plugin: "com.dipien.semanticversion.android"
```

For non-android app projects:

```groovy
buildscript {
    repositories {
        mavenCentral() // or gradlePluginPortal()
    }
    dependencies {
        classpath("com.dipien:semantic-version-gradle-plugin:X.Y.Z")
    }
}
    
apply plugin: "com.dipien.semanticversion"
```

## Configure

### Tasks

#### Print Version
Print the project version. For Android projects, it also prints android app version name & version code.

```
./gradlew printVersion
```

#### Increment version
Increments the project version.

```
./gradlew incrementVersion --versionIncrementType=MAJOR
```

##### versionIncrementType option

Define the type of increment. Possible values: MAJOR, MINOR, PATCH

##### versionIncrementBranch option

The branch where the version increment will be committed and pushed

## Donations

Donations are greatly appreciated. You can help us to pay for our domain and this project development.

* [Donate cryptocurrency](http://coinbase.dipien.com/)
* [Donate with PayPal](http://paypal.dipien.com/)
* [Donate on Patreon](http://patreon.dipien.com/)

## Follow us
* [Twitter](http://twitter.dipien.com)
* [Medium](http://medium.dipien.com)
* [Instagram](http://instagram.dipien.com)
* [Pinterest](http://pinterest.dipien.com)
* [GitHub](http://github.dipien.com)
* [Blog](http://blog.dipien.com)
