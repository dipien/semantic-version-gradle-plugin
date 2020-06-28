package com.semanticversion.android.gradle.plugin

import com.android.build.gradle.AppExtension
import com.semanticversion.SemanticVersionConfig
import com.semanticversion.Version
import com.semanticversion.android.AndroidVersion
import com.semanticversion.gradle.plugin.commons.AbstractTask
import com.semanticversion.gradle.plugin.commons.propertyResolver

open class PrintAndroidVersionTask : AbstractTask() {

    init {
        description = "Prints the android app version name & version code"
    }

    override fun onExecute() {
        println("Version name: " + project.version)

        var minSdkVersion: Int? = null
        project.allprojects.forEach {
            val androidAppExtension = it.extensions.findByType(AppExtension::class.java)
            if (androidAppExtension != null) {
                minSdkVersion = androidAppExtension.defaultConfig.minSdkVersion.apiLevel
            }
        }

        val androidVersion = AndroidVersion(
            SemanticVersionAndroidGradlePlugin.getExtension(project),
            SemanticVersionConfig(project.propertyResolver),
            gitHelper,
            Version(project.version.toString()).baseVersion,
            minSdkVersion!!)
        println("Version code: " + androidVersion.versionCode)
    }
}
