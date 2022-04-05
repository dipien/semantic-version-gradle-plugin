package com.semanticversion.gradle.plugin.commons

import org.gradle.api.DefaultTask
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

abstract class AbstractTask : DefaultTask() {

    @get:Internal
    protected lateinit var gitHelper: GitHelper

    init {
        group = "Semantic Version"
    }

    @TaskAction
    fun doExecute() {
        val commandExecutor = CommandExecutorImpl(project, LogLevel.LIFECYCLE)
        gitHelper = GitHelperImpl(project.propertyResolver, commandExecutor)
        onExecute()
    }

    protected abstract fun onExecute()
}
