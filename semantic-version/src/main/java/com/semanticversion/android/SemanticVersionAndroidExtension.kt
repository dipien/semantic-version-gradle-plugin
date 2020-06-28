package com.semanticversion.android

import com.semanticversion.common.PropertyResolver

open class SemanticVersionAndroidExtension(propertyResolver: PropertyResolver) {

    var versionCodePrefix: Int? = propertyResolver.getIntegerProp(::versionCodePrefix.name)
    var minSdkVersionAsVersionCodePrefix: Boolean = propertyResolver.getRequiredBooleanProp(::minSdkVersionAsVersionCodePrefix.name, true)
    var versionCodeExtraBit: Int = propertyResolver.getRequiredIntegerProp(::versionCodeExtraBit.name, 0)
}
