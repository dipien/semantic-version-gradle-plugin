[![Dipien](https://raw.githubusercontent.com/dipien/dipien-component-builder/master/.github/dipien_logo.png)](http://www.dipien.com)

# Semantic Version Gradle Plugin

## Features

## Setup

Add the following configuration to your root `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/dipien/semantic-version-gradle-plugin/releases/latest)

Using the plugins DSL:

```groovy
plugins {
  id "com.dipien.semanticversion" version "X.Y.Z" // or "com.dipien.semanticversion.android" for android apps
}
```

Using legacy plugin application:

```groovy
buildscript {
    repositories {
        mavenCentral() // or gradlePluginPortal()
    }
    dependencies {
        classpath("com.dipien:semantic-version-gradle-plugin:X.Y.Z")
    }
}
    
apply plugin: "com.dipien.semanticversion" // or "com.dipien.semanticversion.android" for android apps
```
## Configure

### Tasks

#### Print Version
Print the project version. For Android projects, it also prints android app version name & version code.

```
./gradlew printVersion
```

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
