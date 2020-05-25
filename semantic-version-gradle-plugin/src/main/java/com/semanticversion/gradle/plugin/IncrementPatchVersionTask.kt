package com.semanticversion.gradle.plugin

open class IncrementPatchVersionTask : AbstractIncrementVersionTask() {

	companion object {
		const val TASK_NAME = "incrementPatchVersion"
	}

	init {
		description = "Increments the patch version (X.Y.Z) -> (X.Y.Z+1)"
	}

	override fun incrementVersion(version: Version) {
		version.incrementPatch()
	}
}