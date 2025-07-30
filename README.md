[![Dipien](https://raw.githubusercontent.com/dipien/dipien-component-builder/master/.github/dipien_logo.png)](https://medium.com/dipien)

# Semantic Version Gradle Plugin

With the Semantic Version Gradle Plugin you can automatically apply the [Semantic Versioning](https://semver.org/) specification to your Gradle project.

**You can read more details about this plugin on this [article](https://medium.com/dipien/automatic-semantic-versioning-on-your-gradle-project-6343b626b27b)**

## Usage

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
version = "1.0.0" // Assign your project version here
apply plugin: "com.dipien.semantic-version"
```

Define your project version on your root `build.gradle[.kts]` using the Semantic Versioning specification but without any classifier. It's important you define the version before applying the plugin. The plugin will assign the root project version to all its subprojects.

## Printing the project version
The `printVersion` task prints the project version on the console.

```
./gradlew printVersion
OUTPUT: Version: 1.0.0-SNAPSHOT
```

By default, the `SNAPSHOT` classifier is enabled. You can use the `-Psnapshot=false` parameter any time you want to use a stable version. For example when publishing an artifact, generating the release android app bundle, etc.

```
./gradlew printVersion -Psnapshot=false
OUTPUT: Version: 1.0.0
```

You can use alpha, beta or rc classifiers:

```
./gradlew printVersion -Palpha=true
OUTPUT: Version: 1.0.0-ALPHA

./gradlew printVersion -Pbeta=true
OUTPUT: 1.0.0-BETA

./gradlew printVersion -Prc=true
OUTPUT: 1.0.0-RC
```

You can also assign custom classifiers using the `versionClassifier` parameter:

```
./gradlew printVersion -PversionClassifier=canary
OUTPUT: 1.0.0-canary
```

## Incrementing the project version

The `incrementVersion` task increments the project version on your root `build.gradle[.kts]` file. The `versionIncrementType` option defines the type of increment: `MAJOR`, `MINOR` or `PATCH`

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

You can configure the commit message adding a prefix with the `commitMessagePrefix` option. For example

```
./gradlew incrementVersion --commitMessagePrefix="[my prefix] " --versionIncrementType=MAJOR --versionIncrementBranch=master
```

## Advanced Configuration
All the configuration properties can be added using any of the following ways:

* As a command-line parameter:

```
./gradlew ... -PgitUserEmail="email@mail.com"
```

* As a property on a `gradle.properties` file:

```
gitUserEmail = "email@mail.com"
```

* As an extra property on the root `build.gradle[.kts]`, before applying the plugin:

```
ext.gitUserEmail = "email@mail.com"
```

* As a System Environment property

### Maximum Major Version
The `maximumMajorVersion` parameter represents the maximum value allowed for the `MAJOR`. The default value is `99`, so by default `99.99.99` is the maximum supported version.

### Maximum Minor Version
The `maximumMinorVersion` parameter represents the maximum value allowed for the `MINOR`. The default value is `99`, so by default `99.99.99` is the maximum supported version.

### Maximum Patch Version
The `maximumPatchVersion` parameter represents the maximum value allowed for the `PATCH`. The default value is `99`, so by default `99.99.99` is the maximum supported version.

### Snapshot
The `snapshot` parameter represents whether the version should have the `-SNAPSHOT` classifier or not. By default, all the versions are considered as snapshots, so all the local builds don't interfere with the release builds.

### Alpha
The `alpha` parameter represents whether the version should have the `-ALPHA` classifier or not.

### Beta
The `beta` parameter represents whether the version should have the `-BETA` classifier or not.

### RC
The `rc` parameter represents whether the version should have the `-RC` classifier or not.

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

The Semantic Version Gradle Plugin automatically generates and configures the `versionCode` and `versionName` on the `defaultConfig` section at build time. It uses the version defined at the root `build.gradle[.kts]` to configure those fields. **It's important to remember you don't have to set those fields on the `defaultConfig` section.**

### Simple versioning scheme
Thanks to the introduction of the App Bundle format, in most cases, you are not going to need the multiple APKs support.
So you can just use a 6 digits version code that represents the semantic version: the first two digits for the `MAJOR` version, then two for the `MINOR` and the last two for the `PATCH` version.

![](wiki/versioncode.png)

As you can see, you can go from version `0.0.1` to `99.99.99`. So, you have room for more than 192 years of versions, releasing weekly and without taking into account the hotfixes !!!
This simple versioning scheme is the default scheme on the plugin.

For Android projects, the `printVersion` task prints the project version, the android app version name & version code.

```
./gradlew printVersion
OUTPUT:
Version: 1.0.0-SNAPSHOT
Version code: 10000
Version name: 1.0.0-SNAPSHOT
```

Given that the default value of the `maximumVersion` parameter is `99`, the maximum generated version code will be `999999`. Be careful, increasing this value, impacts the generated version code, which is limited to `2100000000`

## More info

You can read more details about this plugin on this [article](https://medium.com/dipien/automatic-semantic-versioning-on-your-gradle-project-6343b626b27b)
