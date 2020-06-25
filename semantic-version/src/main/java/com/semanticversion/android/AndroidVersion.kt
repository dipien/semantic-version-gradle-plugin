package com.semanticversion.android

import com.semanticversion.common.GitHelper
import com.semanticversion.common.PropertyResolver
import com.semanticversion.Version

class AndroidVersion : Version {

    private companion object {
        fun fromVersionCodeToVersionName(versionCode: Int): String {

            val versionCodeString = versionCode.toString()

            var beginIndex = versionCodeString.length - 2
            var endIndex = versionCodeString.length
            val patch = Integer.parseInt(versionCodeString.substring(beginIndex, endIndex))

            beginIndex -= 2
            endIndex -= 2
            val minor = Integer.parseInt(versionCodeString.substring(beginIndex, endIndex))

            beginIndex -= 2
            endIndex -= 2
            val major = Integer.parseInt(versionCodeString.substring(beginIndex, endIndex))

            return "$major.$minor.$patch"
        }
    }

    var versionCodePrefix: Int? = null
    var versionCodeExtraBit: Int? = null
    val versionCode: Int

    override val defaultMaximumVersion: Int
        get() = 99

    constructor(propertyResolver: PropertyResolver, gitHelper: GitHelper, baseVersion: String) : super(propertyResolver, gitHelper, baseVersion) {
        versionCodePrefix = propertyResolver.getIntegerProp("VERSION_CODE_PREFIX")
        if (versionCodePrefix == null) {
            versionCodePrefix = propertyResolver.getIntegerProp("MIN_SDK_VERSION", 0)
        }
        versionCodeExtraBit = propertyResolver.getIntegerProp("VERSION_CODE_EXTRA_BIT", 0)
        versionCode = versionCodePrefix!! * 10000000 + versionCodeExtraBit!! * 1000000 + versionMajor!! * 10000 + versionMinor!! * 100 + versionPatch!!
    }

    constructor(versionCode: Int) : super(fromVersionCodeToVersionName(versionCode)) {
        this.versionCode = versionCode
    }
}
