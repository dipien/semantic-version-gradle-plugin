package com.semanticversion.gradle.plugin

import com.semanticversion.gradle.plugin.commons.PropertyResolver

open class SemanticVersionExtension(propertyResolver: PropertyResolver) {

    var gitUserName: String? = propertyResolver.getStringProp(::gitUserName.name)
    var gitUserEmail: String? = propertyResolver.getStringProp(::gitUserEmail.name)

    // The maximum number the MAJOR, MINOR or PATCH version can achieve. If it is not specified,
    // 99 is used for Android projects and 999 for non Android projects
    var maximumVersion: Int? = propertyResolver.getIntegerProp(::maximumVersion.name)


    var versionClassifier: String? = propertyResolver.getStringProp(::versionClassifier.name)
    var snapshot: Boolean? = propertyResolver.getBooleanProp(::snapshot.name)
    var alpha: Boolean? = propertyResolver.getBooleanProp(::alpha.name)
    var beta: Boolean? = propertyResolver.getBooleanProp(::beta.name)
}
