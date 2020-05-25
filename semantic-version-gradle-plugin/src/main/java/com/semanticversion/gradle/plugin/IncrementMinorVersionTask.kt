package com.semanticversion.gradle.plugin

open class IncrementMinorVersionTask : AbstractIncrementVersionTask() {

    companion object {
        const val TASK_NAME = "incrementMinorVersion"
    }

    init {
        description = "Increments the minor version (X.Y.Z) -> (X.Y+1.0)"
    }

    override fun incrementVersion(version: Version) {
        version.incrementMinor()
    }
}
