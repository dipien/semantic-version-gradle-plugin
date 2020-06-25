package com.semanticversion.gradle.plugin

import com.semanticversion.gradle.plugin.android.AndroidVersion
import com.semanticversion.gradle.plugin.commons.GitHelper
import com.semanticversion.gradle.plugin.commons.PropertyResolver

open class SemanticVersionGradlePluginExtension(val propertyResolver: PropertyResolver, val gitHelper: GitHelper, val baseVersion: String) {

    fun calculateVersionCode(): Int {
        return AndroidVersion(propertyResolver, gitHelper, baseVersion).versionCode
    }
}
