package com.semanticversion.gradle.plugin

import com.semanticversion.VersionIncrementType
import com.semanticversion.gradle.plugin.commons.AbstractTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option

open class IncrementVersionTask : AbstractTask() {

    companion object {
        const val TASK_NAME = "incrementVersion"
    }

    init {
        description = "Increment the project version"
    }

    @get:Input
    @Option(description = "")
    lateinit var versionIncrementType: String

    @get:Input
    @get:Optional
    @Option(description = "")
    var versionIncrementBranch: String? = null

    override fun onExecute() {
        IncrementVersionHelper.increment(
            project,
            VersionIncrementType.valueOf(versionIncrementType.toUpperCase()),
            versionIncrementBranch,
            commandExecutor,
            SemanticVersionGradlePlugin.getExtension(project)
        )
    }
}
