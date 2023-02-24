package com.semanticversion

open class Version {

    companion object {
        const val VERSION_CLASSIFIER_SEPARATOR = "-"
        const val SNAPSHOT_CLASSIFIER = "SNAPSHOT"
        const val ALPHA_CLASSIFIER = "ALPHA"
        const val BETA_CLASSIFIER = "BETA"
        const val RC_CLASSIFIER = "RC"
        const val BASE_VERSION_SEPARATOR = "."
        // const val LOCAL_CLASSIFIER = "LOCAL"
        // const val VERSION_TIMESTAMP_FORMAT = "YYYYMMddHHmmss"
    }

    var versionMajor: Int? = null
    var versionMinor: Int? = null
    var versionPatch: Int? = null
    var versionClassifier: String? = null
    var isSnapshot: Boolean = true
    var isAlpha: Boolean = false
    var isBeta: Boolean = false
    var isRc: Boolean = false

    // TODO Add support to this
    // var isVersionTimestampEnabled: Boolean = false
    // var isLocal: Boolean = false
    // var featureName: String? = null
    // var featureBranchPrefix: String? = null

    var maximumMajorVersion: Int?
    var maximumMinorVersion: Int?
    var maximumPatchVersion: Int?

    protected open val defaultMaximumVersion: Int
        get() = 99

    val baseVersion: String
        get() = versionMajor.toString() + BASE_VERSION_SEPARATOR + versionMinor + BASE_VERSION_SEPARATOR + versionPatch

    constructor(versionMajor: Int, versionMinor: Int, versionPatch: Int) {
        maximumMajorVersion = defaultMaximumVersion
        maximumMinorVersion = defaultMaximumVersion
        maximumPatchVersion = defaultMaximumVersion
        this.versionMajor = versionMajor
        this.versionMinor = versionMinor
        this.versionPatch = versionPatch
        validateBaseVersion()
    }

    constructor(version: String, maximumMajorVersion: Int? = null, maximumMinorVersion: Int? = null, maximumPatchVersion: Int? = null) {
        this.maximumMajorVersion = maximumMajorVersion ?: defaultMaximumVersion
        this.maximumMinorVersion = maximumMinorVersion ?: defaultMaximumVersion
        this.maximumPatchVersion = maximumPatchVersion ?: defaultMaximumVersion
        val split = version.split(VERSION_CLASSIFIER_SEPARATOR)
        val baseVersion = split[0]
        parseBaseVersion(baseVersion)
        if (split.size > 1) {
            versionClassifier = split[1]
            parseVersionClassifier(versionClassifier!!)
            // isLocal = versionClassifier == LOCAL_CLASSIFIER
            // isVersionTimestampEnabled = false
        } else {
            isSnapshot = false
            isAlpha = false
            isBeta = false
            // isLocal = false
            // isVersionTimestampEnabled = false
        }
    }

    constructor(baseVersion: String, config: SemanticVersionConfig) {
        maximumMajorVersion = config.maximumMajorVersion ?: defaultMaximumVersion
        maximumMinorVersion = config.maximumMinorVersion ?: defaultMaximumVersion
        maximumPatchVersion = config.maximumPatchVersion ?: defaultMaximumVersion
        parseBaseVersion(baseVersion)

        versionClassifier = config.versionClassifier
        if (versionClassifier == null) {
            // featureBranchPrefix = config.featureBranchPrefix
            // if (!featureBranchPrefix.isNullOrEmpty()) {
            //     val gitBranch = gitHelper.getGitBranch()
            //     val isFeatureBranch = gitBranch?.startsWith(featureBranchPrefix!!) ?: false
            //     if (isFeatureBranch) {
            //         featureName = gitBranch!!.replace(featureBranchPrefix!!, "")
            //         versionClassifier = featureName
            //     }
            // }
            //
            // config.local?.let {
            //     isLocal = it
            // }
            // if (isLocal) {
            //     if (versionClassifier == null) {
            //         versionClassifier = ""
            //     } else {
            //         versionClassifier += VERSION_CLASSIFIER_SEPARATOR
            //     }
            //     versionClassifier += LOCAL_CLASSIFIER
            // }
            //
            // config.versionTimestampEnabled?.let {
            //     isVersionTimestampEnabled = it
            // }
            // if (isVersionTimestampEnabled) {
            //     if (versionClassifier == null) {
            //         versionClassifier = ""
            //     } else {
            //         versionClassifier += VERSION_CLASSIFIER_SEPARATOR
            //     }
            //     versionClassifier += format(now(), VERSION_TIMESTAMP_FORMAT)
            // }

            if (config.snapshot == true || config.snapshot == null) {
                versionClassifier = SNAPSHOT_CLASSIFIER

                isSnapshot = true
                isAlpha = false
                isBeta = false
                isRc = false
            } else {
                isSnapshot = false
                isAlpha = false
                isBeta = false
                isRc = false
            }

            if (config.alpha == true) {
                versionClassifier = ALPHA_CLASSIFIER

                isSnapshot = false
                isAlpha = true
                isBeta = false
                isRc = false
            }

            if (config.beta == true) {
                versionClassifier = BETA_CLASSIFIER

                isSnapshot = false
                isAlpha = false
                isBeta = true
                isRc = false
            }

            if (config.rc == true) {
                versionClassifier = RC_CLASSIFIER

                isSnapshot = false
                isAlpha = false
                isBeta = false
                isRc = true
            }
        } else {
            parseVersionClassifier(versionClassifier!!)
            // isLocal = false
            // isVersionTimestampEnabled = false
        }
    }

    private fun parseVersionClassifier(versionClassifier: String) {
        when (versionClassifier) {
            SNAPSHOT_CLASSIFIER -> {
                isSnapshot = true
                isBeta = false
                isAlpha = false
                isRc = false
            }
            ALPHA_CLASSIFIER -> {
                isSnapshot = false
                isBeta = false
                isAlpha = true
                isRc = false
            }
            BETA_CLASSIFIER -> {
                isSnapshot = false
                isBeta = true
                isAlpha = false
                isRc = false
            }
            RC_CLASSIFIER -> {
                isSnapshot = false
                isBeta = false
                isAlpha = false
                isRc = true
            }
            else -> {
                isSnapshot = false
                isBeta = false
                isAlpha = false
                isRc = false
            }
        }
    }

    private fun parseBaseVersion(baseVersion: String) {
        val versionSplit = baseVersion.split(BASE_VERSION_SEPARATOR)
        if (versionSplit.size != 3) {
            throw RuntimeException("The version [$baseVersion] is not a valid Semantic Versioning")
        }
        versionMajor = versionSplit[0].toInt()
        versionMinor = versionSplit[1].toInt()
        versionPatch = versionSplit[2].toInt()
        validateBaseVersion()
    }

    private fun validateBaseVersion() {
        if (versionMajor!! > maximumMajorVersion!! || versionMajor!! < 0) {
            throw RuntimeException("The version major [$versionMajor] should be a number between 0 and $maximumMajorVersion")
        }
        if (versionMinor!! > maximumMinorVersion!! || versionMinor!! < 0) {
            throw RuntimeException("The version minor [$versionMinor] should be a number between 0 and $maximumMinorVersion")
        }
        if (versionPatch!! > maximumPatchVersion!! || versionPatch!! < 0) {
            throw RuntimeException("The version patch [$versionPatch] should be a number between 0 and $maximumPatchVersion")
        }
    }

    fun incrementMajor() {
        if (versionMajor!! < maximumMajorVersion!!) {
            versionMajor = versionMajor!! + 1
            versionMinor = 0
            versionPatch = 0
        } else {
            throw RuntimeException("The version major [$versionMajor] can't be incremented. Maximum value achieved.")
        }
    }

    fun incrementMinor() {
        if (versionMinor!! < maximumMinorVersion!!) {
            versionMinor = versionMinor!! + 1
            versionPatch = 0
        } else {
            incrementMajor()
        }
    }

    fun incrementPatch() {
        if (versionPatch!! < maximumPatchVersion!!) {
            versionPatch = versionPatch!! + 1
        } else {
            incrementMinor()
        }
    }

    override fun toString(): String {
        var versionName = baseVersion
        if (!versionClassifier.isNullOrEmpty()) {
            versionName += "$VERSION_CLASSIFIER_SEPARATOR$versionClassifier"
        }
        return versionName
    }
}
