package com.semanticversion.android.gradle.plugin

import com.android.build.gradle.AppExtension
import com.semanticversion.gradle.plugin.commons.AbstractTask

open class PrintAndroidVersionTask : AbstractTask() {

    init {
        description = "Prints the android app version name & version code"
    }

    override fun onExecute() {
        println("Version: " + project.version)

        var versionName: String? = null
        var versionCode: Int? = null
        project.allprojects.forEach {
            val androidAppExtension = it.extensions.findByType(AppExtension::class.java)
            if (androidAppExtension != null) {
                versionName = androidAppExtension.defaultConfig.versionName
                versionCode = androidAppExtension.defaultConfig.versionCode
            }
        }
        println("Version code: $versionCode")
        println("Version name: $versionName")
    }
}
