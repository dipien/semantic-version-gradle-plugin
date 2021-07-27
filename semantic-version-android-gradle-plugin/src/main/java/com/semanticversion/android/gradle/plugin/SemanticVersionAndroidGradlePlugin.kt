package com.semanticversion.android.gradle.plugin

import com.android.build.gradle.AppExtension
import com.semanticversion.SemanticVersionConfig
import com.semanticversion.android.AndroidVersion
import com.semanticversion.gradle.plugin.SemanticVersionGradlePlugin
import com.semanticversion.android.gradle.plugin.SemanticVersionAndroidExtension
import com.semanticversion.gradle.plugin.PrintVersionTask
import com.semanticversion.gradle.plugin.SemanticVersionExtension
import com.semanticversion.gradle.plugin.commons.propertyResolver
import org.gradle.api.Project

open class SemanticVersionAndroidGradlePlugin : SemanticVersionGradlePlugin() {

    companion object {
        fun getExtension(project: Project): SemanticVersionAndroidExtension {
            return project.extensions.findByType(SemanticVersionAndroidExtension::class.java)!!
        }
    }

    override fun apply(project: Project) {
        super.apply(project)

        val semanticVersionAndroidExtension = extension as SemanticVersionAndroidExtension

        project.allprojects.forEach { eachProject ->
            eachProject.afterEvaluate {
                val androidAppExtension = it.extensions.findByType(AppExtension::class.java)
                if (androidAppExtension != null) {
                    androidAppExtension.defaultConfig.versionCode = AndroidVersion(
                        baseVersion,
                        semanticVersionAndroidExtension.versionCodePrefix,
                        semanticVersionAndroidExtension.minSdkVersionAsVersionCodePrefix,
                        semanticVersionAndroidExtension.versionCodeExtraBit,
                        SemanticVersionConfig(project.propertyResolver),
                        androidAppExtension.defaultConfig.minSdkVersion.apiLevel).versionCode
                    androidAppExtension.defaultConfig.versionName = project.version.toString()
                }
            }
        }
    }

    override fun getExtensionClass(): Class<out SemanticVersionExtension> {
        return SemanticVersionAndroidExtension::class.java
    }

    override fun createPrintTask() {
        project.tasks.create(PrintVersionTask.TASK_NAME, PrintAndroidVersionTask::class.java)
    }
}
