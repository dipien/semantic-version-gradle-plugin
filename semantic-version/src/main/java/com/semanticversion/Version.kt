package com.semanticversion

open class Version {

    companion object {
        const val VERSION_CLASSIFIER_SEPARATOR = "-"
        const val SNAPSHOT_CLASSIFIER = "SNAPSHOT"
        const val BETA_CLASSIFIER = "BETA"
        const val ALPHA_CLASSIFIER = "ALPHA"
        const val BASE_VERSION_SEPARATOR = "."
        // const val LOCAL_CLASSIFIER = "LOCAL"
        // const val VERSION_TIMESTAMP_FORMAT = "YYYYMMddHHmmss"
    }

    var versionMajor: Int? = null
    var versionMinor: Int? = null
    var versionPatch: Int? = null
    var versionClassifier: String? = null
    var isSnapshot: Boolean = true
    var isBeta: Boolean = false
    var isAlpha: Boolean = false

    // TODO Add support to this
    // var isVersionTimestampEnabled: Boolean = false
    // var isLocal: Boolean = false
    // var featureName: String? = null
    // var featureBranchPrefix: String? = null

    var maximumVersion: Int?

    protected open val defaultMaximumVersion: Int
        get() = 99

    val baseVersion: String
        get() = versionMajor.toString() + BASE_VERSION_SEPARATOR + versionMinor + BASE_VERSION_SEPARATOR + versionPatch

    constructor(versionMajor: Int, versionMinor: Int, versionPatch: Int) {
        maximumVersion = defaultMaximumVersion
        this.versionMajor = versionMajor
        this.versionMinor = versionMinor
        this.versionPatch = versionPatch
        validateBaseVersion()
    }

    constructor(version: String) {
        maximumVersion = defaultMaximumVersion
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
        maximumVersion = config.maximumVersion ?: defaultMaximumVersion
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

            if (config.alpha == true) {
                versionClassifier = ALPHA_CLASSIFIER

                isAlpha = true
                isBeta = false
                isSnapshot = false
            } else {
                if (config.beta == true) {
                    versionClassifier = BETA_CLASSIFIER

                    isAlpha = false
                    isBeta = true
                    isSnapshot = false
                } else {
                    if (config.snapshot == true || config.snapshot == null) {
                        versionClassifier = SNAPSHOT_CLASSIFIER

                        isAlpha = false
                        isBeta = false
                        isSnapshot = true
                    } else {
                        isAlpha = false
                        isBeta = false
                        isSnapshot = false
                    }
                }
            }
        } else {
            parseVersionClassifier(versionClassifier!!)
            // isLocal = false
            // isVersionTimestampEnabled = false
        }
    }

    private fun parseVersionClassifier(versionClassifier: String) {
        when (versionClassifier) {
            ALPHA_CLASSIFIER -> {
                isSnapshot = false
                isBeta = false
                isAlpha = true
            }
            BETA_CLASSIFIER -> {
                isSnapshot = false
                isBeta = true
                isAlpha = false
            }
            SNAPSHOT_CLASSIFIER -> {
                isSnapshot = true
                isBeta = false
                isAlpha = false
            }
            else -> {
                isSnapshot = false
                isBeta = false
                isAlpha = false
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
        if (versionMajor!! > maximumVersion!! || versionMajor!! < 0) {
            throw RuntimeException("The version major [$versionMajor] should be a number between 0 and $maximumVersion")
        }
        if (versionMinor!! > maximumVersion!! || versionMinor!! < 0) {
            throw RuntimeException("The version minor [$versionMinor] should be a number between 0 and $maximumVersion")
        }
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
            versionName += "$VERSION_CLASSIFIER_SEPARATOR$versionClassifier"
        }
        return versionName
    }
}
