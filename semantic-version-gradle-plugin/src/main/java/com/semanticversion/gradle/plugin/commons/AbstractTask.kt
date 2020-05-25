package com.semanticversion.gradle.plugin.commons

import com.semanticversion.gradle.plugin.SemanticVersionGradlePlugin
import com.semanticversion.gradle.plugin.SemanticVersionGradlePluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

abstract class AbstractTask : DefaultTask() {

    @get:Internal
    protected lateinit var commandExecutor: CommandExecutor

    @get:Internal
    protected lateinit var gitHelper: GitHelper

    @TaskAction
    fun doExecute() {
        commandExecutor = CommandExecutorImpl(project, LogLevel.LIFECYCLE)
        gitHelper = GitHelperImpl(project.propertyResolver, commandExecutor)
        onExecute()
    }

    @Internal
    protected fun getExtension(): SemanticVersionGradlePluginExtension {
        return project.extensions.getByName(SemanticVersionGradlePlugin.EXTENSION_NAME) as SemanticVersionGradlePluginExtension
    }

    protected abstract fun onExecute()
}