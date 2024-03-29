package com.semanticversion.gradle.plugin.print

import com.semanticversion.gradle.plugin.commons.AbstractTask

open class PrintVersionTask : AbstractTask() {

    companion object {
        const val TASK_NAME = "printVersion"
    }

    init {
        description = "Print the project version"
    }

    override fun onExecute() {
        println("Version: " + project.version)
    }
}
