package com.semanticversion.android

import com.semanticversion.SemanticVersionExtension
import com.semanticversion.common.PropertyResolver

open class SemanticVersionAndroidExtension(propertyResolver: PropertyResolver) : SemanticVersionExtension(propertyResolver) {

    var versionCodePrefix: Int? = propertyResolver.getIntegerProp(::versionCodePrefix.name)
    var minSdkVersionAsVersionCodePrefix: Boolean = propertyResolver.getRequiredBooleanProp(::minSdkVersionAsVersionCodePrefix.name, false)
    var versionCodeExtraBit: Int = propertyResolver.getRequiredIntegerProp(::versionCodeExtraBit.name, 0)
}
