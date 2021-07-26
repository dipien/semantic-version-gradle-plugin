package com.semanticversion

import com.semanticversion.common.PropertyResolver

open class SemanticVersionConfig(propertyResolver: PropertyResolver) {

    var maximumVersion: Int? = propertyResolver.getIntegerProp(::maximumVersion.name)
    var versionClassifier: String? = propertyResolver.getStringProp(::versionClassifier.name)
    var snapshot: Boolean? = propertyResolver.getBooleanProp(::snapshot.name)
    // var featureBranchPrefix: String? = propertyResolver.getStringProp(::featureBranchPrefix.name)
    // var versionTimestampEnabled: Boolean? = propertyResolver.getBooleanProp(::featureBranchPrefix.name)
    // var local: Boolean? = propertyResolver.getBooleanProp(::local.name)
}
