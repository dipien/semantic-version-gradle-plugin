[![Dipien](https://raw.githubusercontent.com/dipien/dipien-component-builder/master/.github/dipien_logo.png)](http://www.dipien.com)

# Semantic Version Gradle Plugin

With the Semantic Version Gradle Plugin you can automatically apply the [Semantic Versioning](https://semver.org/) specification to your Gradle project.
Apply the plugin on the root `build.gradle[.kts]`, replacing `X.Y.Z` with the [latest](https://github.com/dipien/semantic-version-gradle-plugin/releases/latest) version:

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

Define your project version on your root `build.gradle[.kts]` using the Semantic Versioning specification but without any classifier:

```groovy
version = "1.0.0"
```

The plugin will assign the root project version to all its subprojects.

## Printing the project version
The `printVersion` task prints the project version on the console.

```
> Task :printVersion
Version: 1.0.0-SNAPSHOT
```

By default, the `SNAPSHOT` classifier is enabled. You can use the `-Psnapshot=false` parameter any time you want to use a stable version. For example when publishing an artifact, generating the release android app bundle, etc.

```
> Task :printVersion -Psnapshot=false
Version: 1.0.0
```

You can use alpha or beta classifiers:

```
> Task :printVersion -Palpha=true
Version: 1.0.0-ALPHA
> Task :printVersion -Pbeta=true
Version: 1.0.0-BETA
```

You can also assign custom classifiers using the `versionClassifier` parameter:

```
> Task :printVersion -PversionClassifier=canary
Version: 1.0.0-canary
```

## Incrementing the project version

The incrementVersion task increments the project version on your root `build.gradle[.kts]` file. The `versionIncrementType` option defines the type of increment: `MAJOR`, `MINOR` or `PATCH`

```
// Increments the major from 1.0.0 to 2.0.0
./gradlew incrementVersion --versionIncrementType=MAJOR
// Increments the minor from 1.0.0 to 1.1.0
./gradlew incrementVersion --versionIncrementType=MINOR
// Increments the patch from 1.0.0 to 1.0.1
./gradlew incrementVersion --versionIncrementType=PATCH
```

If you want to also commit and push the version change, just add the `versionIncrementBranch` option with the HEAD branch.

```
./gradlew incrementVersion --versionIncrementType=MAJOR --versionIncrementBranch=master -PgitUserName=userName -PgitUserEmail=email@mail.com
```

## Advanced Configuration
All the configuration properties can be added using any of the following ways:
Using the `semanticVersion` extension on the root `build.gradle[.kts]`

```groovy
semanticVersion {
    gitUserEmail = "email@mail.com"
}
```

As a command-line parameter:

```
./gradlew ... -PgitUserEmail="email@mail.com"
```

As a property on a `gradle.properties` file:

```
gitUserEmail = "email@mail.com"
```

As an extra property on the root `build.gradle[.kts]`:

```
ext.gitUserEmail = "email@mail.com"
```

As a System Environment property

### Maximum Version
The `maximumVersion` parameter represents the maximum value allowed for a `MAJOR`, `MINOR` or `PATCH`. The default value is `99`, so by default `99.99.99` is the maximum supported version.

### Snapshot
The `snapshot` parameter represents whether the version should have the `-SNAPSHOT` classifier or not. By default, all the versions are considered as snapshots, so all the local builds don't interfere with the release builds.

### Beta
The `beta` parameter represents whether the version should have the `-BETA` classifier or not.

### Alpha
The `alpha` parameter represents whether the version should have the `-ALPHA` classifier or not.

### Version Classifier
The `versionClassifier` parameter represents the classifier appended to the version. You can use this property to define a custom version classifier.

## Android Support
The plugin has some special features for Android projects. You just need to apply the `com.dipien.android.semantic-version` plugin instead of `com.dipien.semantic-version`.
Just add this configuration to your root `build.gradle[.kts]` file, replacing `X.Y.Z` with the [latest](https://github.com/dipien/semantic-version-gradle-plugin/releases/latest) version

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

### Version code & version name
On android you have to define two version fields for an app:
* Version code (`android:versionCode` on your `AndroidManifest.xml`). The version code is an incremental integer value that represents the version of the application code. The greatest value Google Play allows for a version code is `2100000000`.
* Version name (`android:versionName` on your `AndroidManifest.xml`). The version name is a string value that represents the "friendly" version name displayed to the users.

You can get more details [here](http://developer.android.com/tools/publishing/versioning.html#appversioning).

It's a good practice to have a direct relationship between both versions to avoid confusion during the development and release process. At least, you should be able to infer the version name given a version code.
As described [here](http://developer.android.com/google/play/publishing/multiple-apks.html#VersionCodes), the official documentation proposes using a version code scheme that associates the version code and name, and also supports multiple APKs. The plugin uses that scheme but with some changes to also support Semantic Versioning.

### Simple versioning scheme
Thanks to the introduction of the App Bundle format, in most cases, you are not going to need the multiple APKs support.
So you can just use a 6 digits version code that represents the semantic version: the first two digits for the `MAJOR` version, then two for the `MINOR` and the last two for the `PATCH` version.

As you can see, you can go from version `0.0.1` to `99.99.99`. So, you have room for more than 192 years of versions, releasing weekly and without taking into account the hotfixes !!!
This simple versioning scheme is the default scheme on the plugin.

For Android projects, the `printVersion` task prints the project version, the android app version name & version code.

```
> Task :printVersion
Version: 1.0.0-SNAPSHOT
Version code: 10000
Version name: 1.0.0-SNAPSHOT
```

Given that the default value of the `maximumVersion` parameter is `99`, the maximum generated version code will be `999999`. Be careful, increasing this value, it impacts the generated version code, which is limited to `2100000000`

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
