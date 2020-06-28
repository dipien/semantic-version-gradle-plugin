package com.semanticversion.android.gradle.plugin

import com.android.build.gradle.AppExtension
import com.semanticversion.SemanticVersionConfig
import com.semanticversion.android.AndroidVersion
import com.semanticversion.gradle.plugin.SemanticVersionGradlePlugin
import com.semanticversion.android.SemanticVersionAndroidExtension
import com.semanticversion.gradle.plugin.commons.propertyResolver
import org.gradle.api.Project

open class SemanticVersionAndroidGradlePlugin : SemanticVersionGradlePlugin() {

    override fun apply(project: Project) {
        super.apply(project)

        val semVerAndroidExtension = project.extensions.create(EXTENSION_NAME, SemanticVersionAndroidExtension::class.java, project.propertyResolver)

        project.allprojects.forEach {
            it.afterEvaluate {
                val androidAppExtension = it.extensions.findByType(AppExtension::class.java)
                if (androidAppExtension != null) {
                    androidAppExtension.defaultConfig.versionCode = AndroidVersion(
                        semVerAndroidExtension,
                        SemanticVersionConfig(project.propertyResolver),
                        gitHelper,
                        baseVersion,
                        androidAppExtension.defaultConfig.minSdkVersion.apiLevel).versionCode
                    androidAppExtension.defaultConfig.versionName = project.version.toString()
                }
            }
        }

        // TODO Add PrintVersion Code & name task/s
    }
}
