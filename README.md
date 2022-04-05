[![Dipien](https://raw.githubusercontent.com/dipien/dipien-component-builder/master/.github/dipien_logo.png)](http://www.dipien.com)

# Semantic Version Gradle Plugin

Gradle Plugin to automatically apply the [Semantic Versioning](https://semver.org/) scheme to a Gradle project.

## Features

## Setup

Add the following configuration to your root `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/dipien/semantic-version-gradle-plugin/releases/latest)

### Using the plugins DSL

For android app projects:
```groovy
plugins {
  id "com.dipien.android.semantic-version" version "X.Y.Z"
}
```

For non-android app projects:

```groovy
plugins {
  id "com.dipien.semantic-version" version "X.Y.Z"
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
    
apply plugin: "com.dipien.android.semantic-version"
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
    
apply plugin: "com.dipien.semantic-version"
```

## Usage

Define your project version on your root `build.gradle` using the  [Semantic Versioning](https://semver.org/) scheme but without any classifier:

    version = "1.0.0"

By default, the SNAPSHOT classifier is enabled. Use the `-Psnapshot=false` parameter any time you want to assign an stable version. For example when publishing an artifact, generating the release android app bundle, etc.
```
./gradlew printVersion // OUTPUT: Version: 1.0.0-SNAPSHOT
./gradlew printVersion -Psnapshot=false // OUTPUT: Version: 1.0.0
```
If the root project is not specifying an explicit version, a warning will be displayed and v0.1.0 is assigned as the default version.
The plugin will assign the root project version to all its subprojects.

## Configuration

# maximumVersion

The maximum number the MAJOR, MINOR or PATCH version can achieve. 
If it is not specified, 99 is used for Android projects and 999 for non Android projects



### Tasks

#### Print Version
Print the project version. For Android projects, it also prints android app version name & version code.

```
./gradlew printVersion
```

#### Increment version
Increments the project version.

Examples:

Increments the project MAJOR version.
```
./gradlew incrementVersion --versionIncrementType=MAJOR
```

Increments the project MINOR version and push the changes to the **master** branch

```
./gradlew incrementVersion --versionIncrementType=MINOR --versionIncrementBranch=master -PgitUserName=userName -PgitUserEmail=email@mail.com
```

###### versionIncrementType option

Define the type of increment. Possible values: MAJOR, MINOR, PATCH

###### versionIncrementBranch option

The branch where the version increment will be committed and pushed

###### Git User Name

The Git user name used by the commit command. Optional String. 
You can configure it as a command line parameter, as a property on a `gradle.properties` file or as a System Environment property.

    gitUserName = "user"
    
###### Git User Email

The Git user email used by the commit command. Optional String.
You can configure it as a command line parameter, as a property on a `gradle.properties` file or as a System Environment property.

    gitUserEmail = "email@mail.com"

## Sponsor this project

Sponsor this open source project to help us get the funding we need to continue working on it.

* [Donate cryptocurrency](http://coinbase.dipien.com/)
* [Donate with PayPal](http://paypal.dipien.com/)
* [Donate on Patreon](http://patreon.dipien.com/)
* [Become a member of Medium](https://maxirosson.medium.com/membership) [We will receive a portion of your membership fee]

## Follow us
* [Twitter](http://twitter.dipien.com)
* [Medium](http://medium.dipien.com)
* [Instagram](http://instagram.dipien.com)
* [Pinterest](http://pinterest.dipien.com)
* [GitHub](http://github.dipien.com)
