package com.semanticversion.gradle.plugin

import com.semanticversion.gradle.plugin.commons.propertyResolver
import org.gradle.api.Project

open class SemanticVersionGradlePluginExtension(project: Project) {

    var maximumVersion: Int = project.propertyResolver.getRequiredIntegerProp(::maximumVersion.name, 999)
    var versionClassifier: String? = project.propertyResolver.getStringProp(::versionClassifier.name)
    var featureBranchPrefix: String? = project.propertyResolver.getStringProp(::featureBranchPrefix.name)
    var isLocalVersion: Boolean = project.propertyResolver.getRequiredBooleanProp(::isLocalVersion.name, false)
    var isVersionTimestampEnabled: Boolean = project.propertyResolver.getRequiredBooleanProp(::isVersionTimestampEnabled.name, false)
    var versionTimestampFormat: String = project.propertyResolver.getRequiredStringProp(::versionTimestampFormat.name, "YYYYMMddHHmmss")
    var isSnapshotVersion: Boolean = project.propertyResolver.getRequiredBooleanProp(::isSnapshotVersion.name, true)
}
