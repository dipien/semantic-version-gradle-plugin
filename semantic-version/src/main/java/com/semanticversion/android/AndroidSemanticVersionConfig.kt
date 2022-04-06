package com.semanticversion.android

import com.semanticversion.SemanticVersionConfig

class AndroidSemanticVersionConfig(
    maximumVersion: Int? = null,
    versionClassifier: String? = null,
    snapshot: Boolean? = null,
    alpha: Boolean? = null,
    beta: Boolean? = null,
    var versionCodePrefix: Int?,
    var minSdkVersionAsVersionCodePrefix: Boolean,
    var versionCodeExtraBit: Int,
    var minSdkVersion: Int
) : SemanticVersionConfig(maximumVersion, versionClassifier, snapshot, alpha, beta)
