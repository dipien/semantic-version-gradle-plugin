package com.semanticversion.android.gradle.plugin

import com.semanticversion.gradle.plugin.SemanticVersionGradlePluginExtension
import com.semanticversion.gradle.plugin.commons.propertyResolver
import org.gradle.api.Project

open class SemanticVersionAndroidGradlePluginExtension(project: Project) : SemanticVersionGradlePluginExtension(project) {

    var versionCodePrefix: Int? = project.propertyResolver.getIntegerProp(::versionCodePrefix.name)
    var minSdkVersionAsVersionCodePrefix: Boolean = project.propertyResolver.getRequiredBooleanProp(::minSdkVersionAsVersionCodePrefix.name, true)
    var versionCodeExtraBit: Int = project.propertyResolver.getRequiredIntegerProp(::versionCodeExtraBit.name, 0)
}
