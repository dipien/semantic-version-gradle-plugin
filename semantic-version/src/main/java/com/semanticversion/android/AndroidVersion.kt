package com.semanticversion.android

import com.semanticversion.SemanticVersionConfig
import com.semanticversion.Version
import kotlin.math.max

class AndroidVersion : Version {

    private companion object {
        fun fromVersionCodeToVersionName(versionCode: Int): String {

            val versionCodeString = versionCode.toString()

            var beginIndex = versionCodeString.length - 2
            var endIndex = versionCodeString.length
            val patch = versionCodeString.substring(max(beginIndex, 0), max(endIndex, 0)).toIntOrNull() ?: 0

            beginIndex -= 2
            endIndex -= 2
            val minor = versionCodeString.substring(max(beginIndex, 0), max(endIndex, 0)).toIntOrNull() ?: 0

            beginIndex -= 2
            endIndex -= 2
            val major = versionCodeString.substring(max(beginIndex, 0), max(endIndex, 0)).toIntOrNull() ?: 0

            return "$major.$minor.$patch"
        }
    }

    var versionCodePrefix: Int? = null
    var versionCodeExtraBit: Int? = null
    val versionCode: Int

    override val defaultMaximumVersion: Int
        get() = 99

    constructor(
        baseVersion: String,
        extension: SemanticVersionAndroidExtension,
        config: SemanticVersionConfig,
        minSdkVersion: Int
    ) : super(baseVersion, config) {
        if (extension.versionCodePrefix == null) {
            if (extension.minSdkVersionAsVersionCodePrefix) {
                this.versionCodePrefix = minSdkVersion
            } else {
                this.versionCodePrefix = 0
            }
        } else {
            this.versionCodePrefix = extension.versionCodePrefix
        }
        this.versionCodeExtraBit = extension.versionCodeExtraBit
        versionCode = this.versionCodePrefix!! * 10000000 + this.versionCodeExtraBit!! * 1000000 + versionMajor!! * 10000 + versionMinor!! * 100 + versionPatch!!
    }

    constructor(versionCode: Int) : super(fromVersionCodeToVersionName(versionCode)) {
        this.versionCode = versionCode
    }
}
