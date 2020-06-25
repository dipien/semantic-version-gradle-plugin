package com.semanticversion.gradle.plugin.android

import com.semanticversion.gradle.plugin.Version
import com.semanticversion.gradle.plugin.commons.GitHelper
import com.semanticversion.gradle.plugin.commons.PropertyResolver

class AndroidVersion(
    propertyResolver: PropertyResolver,
    gitHelper: GitHelper,
    baseVersion: String
) : Version(propertyResolver, gitHelper, baseVersion) {

    var versionCodePrefix: Int? = null
    var versionCodeExtraBit: Int? = null
    val versionCode: Int

    override val defaultMaximumVersion: Int
        get() = 99

    init {
        versionCodePrefix = propertyResolver.getIntegerProp("VERSION_CODE_PREFIX")
        if (versionCodePrefix == null) {
            versionCodePrefix = propertyResolver.getIntegerProp("MIN_SDK_VERSION", 0)
        }
        versionCodeExtraBit = propertyResolver.getIntegerProp("VERSION_CODE_EXTRA_BIT", 0)
        versionCode = versionCodePrefix!! * 10000000 + versionCodeExtraBit!! * 1000000 + versionMajor!! * 10000 + versionMinor!! * 100 + versionPatch!!
    }
}
