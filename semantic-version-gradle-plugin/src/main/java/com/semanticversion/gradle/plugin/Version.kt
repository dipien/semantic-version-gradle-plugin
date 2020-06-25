package com.semanticversion.gradle.plugin

import com.jdroid.java.date.DateUtils.format
import com.jdroid.java.date.DateUtils.now
import com.semanticversion.gradle.plugin.commons.GitHelper
import com.semanticversion.gradle.plugin.commons.PropertyResolver

open class Version {

    companion object {
        const val VERSION_CLASSIFIER_SEPARATOR = "-"
        const val SNAPSHOT_CLASSIFIER = "SNAPSHOT"
        const val LOCAL_CLASSIFIER = "LOCAL"
        const val BASE_VERSION_SEPARATOR = "."
        const val VERSION_TIMESTAMP_FORMAT = "YYYYMMddHHmmss"
    }

    var versionMajor: Int? = null
    var versionMinor: Int? = null
    var versionPatch: Int? = null
    var versionClassifier: String? = null
    var isVersionTimestampEnabled: Boolean = false
    var isSnapshot: Boolean = true
    var featureName: String? = null
    var featureBranchPrefix: String? = null
    var isLocal: Boolean = false
    var maximumVersion: Int?

    protected open val defaultMaximumVersion: Int
        get() = 999

    val baseVersion: String
        get() = versionMajor.toString() + BASE_VERSION_SEPARATOR + versionMinor + BASE_VERSION_SEPARATOR + versionPatch

    constructor(versionMajor: Int, versionMinor: Int, versionPatch: Int) {
        maximumVersion = defaultMaximumVersion
        this.versionMajor = versionMajor
        this.versionMinor = versionMinor
        this.versionPatch = versionPatch
        this.isSnapshot = false
    }

    constructor(version: String) {
        maximumVersion = defaultMaximumVersion
        val split = version.split(VERSION_CLASSIFIER_SEPARATOR)
        val baseVersion = split[0]
        parseBaseVersion(baseVersion)
        if (split.size > 1) {
            versionClassifier = split[1]
            isSnapshot = versionClassifier == SNAPSHOT_CLASSIFIER

            // TODO Add support to this
            isLocal = false
            isVersionTimestampEnabled = false
        } else {
            isSnapshot = false
            isLocal = false
            isVersionTimestampEnabled = false
        }
    }

    constructor(propertyResolver: PropertyResolver, gitHelper: GitHelper, baseVersion: String) {
        maximumVersion = propertyResolver.getIntegerProp("MAXIMUM_VERSION", defaultMaximumVersion)
        parseBaseVersion(baseVersion)

        versionClassifier = propertyResolver.getStringProp("VERSION_CLASSIFIER", null)
        if (versionClassifier == null) {

            featureBranchPrefix = propertyResolver.getStringProp("FEATURE_BRANCH_PREFIX", "feature/")
            if (!featureBranchPrefix.isNullOrEmpty()) {
                val gitBranch = gitHelper.getGitBranch()
                val isFeatureBranch = gitBranch?.startsWith(featureBranchPrefix!!) ?: false
                if (isFeatureBranch) {
                    featureName = gitBranch!!.replace(featureBranchPrefix!!, "")
                    versionClassifier = featureName
                }
            }

            isLocal = propertyResolver.getRequiredBooleanProp("LOCAL", isLocal)
            if (isLocal) {
                if (versionClassifier == null) {
                    versionClassifier = ""
                } else {
                    versionClassifier += VERSION_CLASSIFIER_SEPARATOR
                }
                versionClassifier += LOCAL_CLASSIFIER
            }

            isVersionTimestampEnabled = propertyResolver.getRequiredBooleanProp("VERSION_TIMESTAMP_ENABLED", isVersionTimestampEnabled)
            if (isVersionTimestampEnabled) {
                if (versionClassifier == null) {
                    versionClassifier = ""
                } else {
                    versionClassifier += VERSION_CLASSIFIER_SEPARATOR
                }
                versionClassifier += format(now(), VERSION_TIMESTAMP_FORMAT)
            }

            isSnapshot = propertyResolver.getRequiredBooleanProp("SNAPSHOT", true)
            if (isSnapshot) {
                if (versionClassifier == null) {
                    versionClassifier = ""
                } else {
                    versionClassifier += VERSION_CLASSIFIER_SEPARATOR
                }
                versionClassifier += SNAPSHOT_CLASSIFIER
            }
        } else {
            isSnapshot = versionClassifier == SNAPSHOT_CLASSIFIER
            // TODO Add support to this
            isLocal = false
            isVersionTimestampEnabled = false
        }
    }

    private fun parseBaseVersion(baseVersion: String) {
        val versionSplit = baseVersion.split(BASE_VERSION_SEPARATOR)
        if (versionSplit.size != 3) {
            throw RuntimeException("The version [$baseVersion] is not a valid Semantic Versioning")
        }
        versionMajor = versionSplit[0].toInt()
        if (versionMajor!! > maximumVersion!! || versionMajor!! < 0) {
            throw RuntimeException("The version major [$versionMajor] should be a number between 0 and $maximumVersion")
        }
        versionMinor = versionSplit[1].toInt()
        if (versionMinor!! > maximumVersion!! || versionMinor!! < 0) {
            throw RuntimeException("The version minor [$versionMinor] should be a number between 0 and $maximumVersion")
        }
        versionPatch = versionSplit[2].toInt()
        if (versionPatch!! > maximumVersion!! || versionPatch!! < 0) {
            throw RuntimeException("The version patch [$versionPatch] should be a number between 0 and $maximumVersion")
        }
    }

    fun incrementMajor() {
        if (versionMajor!! < maximumVersion!!) {
            versionMajor = versionMajor!! + 1
            versionMinor = 0
            versionPatch = 0
        } else {
            throw RuntimeException("The version major [$versionMajor] can't be incremented. Maximum value achieved.")
        }
    }

    fun incrementMinor() {
        if (versionMinor!! < maximumVersion!!) {
            versionMinor = versionMinor!! + 1
            versionPatch = 0
        } else {
            incrementMajor()
        }
    }

    fun incrementPatch() {
        if (versionPatch!! < maximumVersion!!) {
            versionPatch = versionPatch!! + 1
        } else {
            incrementMinor()
        }
    }

    override fun toString(): String {
        var versionName = baseVersion
        if (!versionClassifier.isNullOrEmpty()) {
            versionName += "${VERSION_CLASSIFIER_SEPARATOR}$versionClassifier"
        }
        return versionName
    }
}
