package com.semanticversion.android

import com.semanticversion.Version
import com.semanticversion.utils.countDigits
import kotlin.math.max
import kotlin.math.pow

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

    constructor(
        baseVersion: String,
        config: AndroidSemanticVersionConfig
    ) : super(baseVersion, config) {
        if (config.versionCodePrefix == null) {
            if (config.minSdkVersionAsVersionCodePrefix) {
                this.versionCodePrefix = config.minSdkVersion
            } else {
                this.versionCodePrefix = 0
            }
        } else {
            this.versionCodePrefix = config.versionCodePrefix
        }
        this.versionCodeExtraBit = config.versionCodeExtraBit
        val versionMultiplier = 10.0.pow(maximumVersion!!.countDigits().toDouble()).toInt()
        val versionCodeExtraBitMultiplier = 10.0.pow(versionCodeExtraBit!!.countDigits().toDouble()).toInt()
        versionCode = this.versionCodePrefix!! * versionCodeExtraBitMultiplier * versionMultiplier * versionMultiplier * versionMultiplier + this.versionCodeExtraBit!! * versionMultiplier * versionMultiplier * versionMultiplier + versionMajor!! * versionMultiplier * versionMultiplier + versionMinor!! * versionMultiplier + versionPatch!!
    }

    constructor(versionCode: Int) : super(fromVersionCodeToVersionName(versionCode)) {
        this.versionCode = versionCode
    }
}
