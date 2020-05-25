package com.semanticversion.gradle.plugin

import com.semanticversion.gradle.plugin.commons.AbstractTask

open class PrintVersionTask : AbstractTask() {

	companion object {
		const val TASK_NAME = "printVersion"
	}

	init {
		description = "Prints the current version"
	}

	override fun onExecute() {
		println(project.version)
	}
}