package com.semanticversion.gradle.plugin

import com.semanticversion.android.AndroidVersion
import com.semanticversion.common.GitHelper
import com.semanticversion.common.PropertyResolver

open class SemanticVersionGradlePluginExtension(val propertyResolver: PropertyResolver, val gitHelper: GitHelper, val baseVersion: String) {

    fun calculateVersionCode(): Int {
        return AndroidVersion(propertyResolver, gitHelper, baseVersion).versionCode
    }
}
