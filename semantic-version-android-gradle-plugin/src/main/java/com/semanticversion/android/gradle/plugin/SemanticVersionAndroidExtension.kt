package com.semanticversion.android.gradle.plugin

import com.semanticversion.common.PropertyResolver
import com.semanticversion.gradle.plugin.SemanticVersionExtension

open class SemanticVersionAndroidExtension(propertyResolver: PropertyResolver) : SemanticVersionExtension(propertyResolver) {

    var versionCodePrefix: Int? = propertyResolver.getIntegerProp(::versionCodePrefix.name)
    var minSdkVersionAsVersionCodePrefix: Boolean = propertyResolver.getRequiredBooleanProp(::minSdkVersionAsVersionCodePrefix.name, false)
    var versionCodeExtraBit: Int = propertyResolver.getRequiredIntegerProp(::versionCodeExtraBit.name, 0)
}
