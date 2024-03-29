package com.semanticversion.gradle.plugin.increment

import com.semanticversion.VersionIncrementType
import com.semanticversion.gradle.plugin.commons.AbstractTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option
import java.util.Locale

open class IncrementVersionTask : AbstractTask() {

    companion object {
        const val TASK_NAME = "incrementVersion"
    }

    init {
        description = "Increment the project version"
    }

    @get:Input
    @get:Optional
    @Option(description = "")
    var gitUserName: String? = null

    @get:Input
    @get:Optional
    @Option(description = "")
    var gitUserEmail: String? = null

    @get:Input
    @get:Optional
    @Option(description = "")
    var versionIncrementType: String? = null

    @get:Input
    @get:Optional
    @Option(description = "")
    var versionIncrementBranch: String? = null

    @get:Internal
    var maximumMajorVersion: Int? = null

    @get:Internal
    var maximumMinorVersion: Int? = null

    @get:Internal
    var maximumPatchVersion: Int? = null

    @get:Input
    @get:Optional
    @Option(description = "")
    var commitMessagePrefix: String? = null

    override fun onExecute() {

        requireNotNull(versionIncrementType) { "The '${::versionIncrementType.name}' property is required" }

        val buildGradleFile = project.buildFile
        val newVersion = IncrementVersionHelper.increment(
            buildGradleFile,
            VersionIncrementType.valueOf(versionIncrementType!!.uppercase(Locale.getDefault())),
            versionIncrementBranch,
            maximumMajorVersion,
            maximumMinorVersion,
            maximumPatchVersion,
            commitMessagePrefix,
            gitUserName,
            gitUserEmail,
            gitHelper
        )
        project.version = newVersion.toString()
    }
}
