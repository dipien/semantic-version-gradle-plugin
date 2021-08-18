package com.semanticversion.gradle.plugin

import com.semanticversion.gradle.plugin.commons.PropertyResolver

open class SemanticVersionExtension(propertyResolver: PropertyResolver) {

    var gitUserName: String? = propertyResolver.getStringProp(::gitUserName.name)
    var gitUserEmail: String? = propertyResolver.getStringProp(::gitUserEmail.name)

    var maximumVersion: Int? = propertyResolver.getIntegerProp(::maximumVersion.name)
    var versionClassifier: String? = propertyResolver.getStringProp(::versionClassifier.name)
    var snapshot: Boolean? = propertyResolver.getBooleanProp(::snapshot.name)
}
