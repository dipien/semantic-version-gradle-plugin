package com.semanticversion

open class SemanticVersionConfig(
    var maximumVersion: Int? = null,
    var versionClassifier: String? = null,
    var snapshot: Boolean? = null,
    var alpha: Boolean? = null,
    var beta: Boolean? = null
)

// var featureBranchPrefix: String? = propertyResolver.getStringProp(::featureBranchPrefix.name)
// var versionTimestampEnabled: Boolean? = propertyResolver.getBooleanProp(::featureBranchPrefix.name)
// var local: Boolean? = propertyResolver.getBooleanProp(::local.name)
