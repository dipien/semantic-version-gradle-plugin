package com.semanticversion.android.gradle.plugin

import com.android.build.gradle.AppExtension
import com.semanticversion.android.AndroidVersion
import com.semanticversion.gradle.plugin.SemanticVersionGradlePlugin
import org.gradle.api.Project

open class SemanticVersionAndroidGradlePlugin : SemanticVersionGradlePlugin() {

    override fun apply(project: Project) {
        super.apply(project)

        project.allprojects.forEach {
            it.afterEvaluate {
                val androidAppExtension = it.extensions.findByType(AppExtension::class.java)
                if (androidAppExtension != null) {
                    androidAppExtension.defaultConfig.versionCode = AndroidVersion(propertyResolver, gitHelper, baseVersion).versionCode
                    androidAppExtension.defaultConfig.versionName = project.version.toString()
                }
            }
        }
    }
}
