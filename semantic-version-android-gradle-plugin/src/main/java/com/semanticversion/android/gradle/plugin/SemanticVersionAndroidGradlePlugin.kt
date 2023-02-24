package com.semanticversion.android.gradle.plugin

import com.android.build.gradle.AppExtension
import com.semanticversion.android.AndroidSemanticVersionConfig
import com.semanticversion.android.AndroidVersion
import com.semanticversion.gradle.plugin.SemanticVersionGradlePlugin
import com.semanticversion.gradle.plugin.print.PrintVersionTask
import com.semanticversion.gradle.plugin.SemanticVersionExtension
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
                    val config = AndroidSemanticVersionConfig(
                        semanticVersionConfig.maximumMajorVersion,
                        semanticVersionConfig.maximumMinorVersion,
                        semanticVersionConfig.maximumPatchVersion,
                        semanticVersionConfig.versionClassifier,
                        semanticVersionConfig.snapshot,
                        semanticVersionConfig.alpha,
                        semanticVersionConfig.beta,
                        semanticVersionConfig.rc,
                        semanticVersionAndroidExtension.versionCodePrefix,
                        semanticVersionAndroidExtension.minSdkVersionAsVersionCodePrefix,
                        semanticVersionAndroidExtension.versionCodeExtraBit,
                        androidAppExtension.defaultConfig.minSdkVersion!!.apiLevel)

                    if (androidAppExtension.defaultConfig.versionCode != null) {
                        throw RuntimeException("You shouldn't define the versionCode on the defaultConfig section. The Semantic Versioning Gradle Plugin automatically set that value on build time.")
                    }

                    if (androidAppExtension.defaultConfig.versionName != null) {
                        throw RuntimeException("You shouldn't define the versionName on the defaultConfig section. The Semantic Versioning Gradle Plugin automatically set that value on build time.")
                    }

                    androidAppExtension.defaultConfig.versionCode = AndroidVersion(baseVersion, config).versionCode
                    androidAppExtension.defaultConfig.versionName = project.version.toString()
                }
            }
        }
    }

    override fun getExtensionClass(): Class<out SemanticVersionExtension> {
        return SemanticVersionAndroidExtension::class.java
    }

    override fun createPrintTask() {
        val printAndroidVersionTask = project.tasks.create(PrintVersionTask.TASK_NAME, PrintAndroidVersionTask::class.java)
        printAndroidVersionTask.notCompatibleWithConfigurationCache("Not implemented yet")
    }
}
