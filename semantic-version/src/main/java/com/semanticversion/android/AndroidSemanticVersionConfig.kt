package com.semanticversion.android

import com.semanticversion.SemanticVersionConfig

class AndroidSemanticVersionConfig(
    maximumMajorVersion: Int? = null,
    maximumMinorVersion: Int? = null,
    maximumPatchVersion: Int? = null,
    versionClassifier: String? = null,
    snapshot: Boolean? = null,
    beta: Boolean? = null,
    alpha: Boolean? = null,
    var versionCodePrefix: Int?,
    var minSdkVersionAsVersionCodePrefix: Boolean,
    var versionCodeExtraBit: Int,
    var minSdkVersion: Int
) : SemanticVersionConfig(maximumMajorVersion, maximumMinorVersion, maximumPatchVersion, versionClassifier, snapshot, beta, alpha)
