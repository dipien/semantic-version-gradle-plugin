package com.semanticversion.gradle.plugin

open class IncrementMajorVersionTask : AbstractIncrementVersionTask() {

	companion object {
		const val TASK_NAME= "incrementMajorVersion"
	}

	init {
		description = "Increments the major version (X.Y.Z) -> (X+1.0.0)"
	}

	override fun incrementVersion(version: Version) {
		version.incrementMajor()
	}
}