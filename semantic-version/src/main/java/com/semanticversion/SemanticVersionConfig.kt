package com.semanticversion

import com.semanticversion.common.PropertyResolver

open class SemanticVersionConfig(propertyResolver: PropertyResolver) {

    var maximumVersion: Int = propertyResolver.getRequiredIntegerProp(::maximumVersion.name, 999)
    var versionClassifier: String? = propertyResolver.getStringProp(::versionClassifier.name)
    var featureBranchPrefix: String? = propertyResolver.getStringProp(::featureBranchPrefix.name)
    var versionTimestampEnabled: Boolean = propertyResolver.getRequiredBooleanProp(::featureBranchPrefix.name, false)
    var snapshot: Boolean = propertyResolver.getRequiredBooleanProp(::snapshot.name, true)
    var local: Boolean = propertyResolver.getRequiredBooleanProp(::local.name, false)
}
