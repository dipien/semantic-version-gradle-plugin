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

        println()
        println("***********************************************************")
        println("* You can support this project, so we can continue improving it:")
        println("* - Donate cryptocurrency: http://coinbase.dipien.com/")
        println("* - Donate  with credit card: http://kofi.dipien.com/")
        println("* - Donate on Patreon: http://patreon.dipien.com/")
        println("* - Become a member of Medium (We will receive a portion of your membership fee): https://membership.medium.dipien.com")
        println("* Thanks !!!")
        println("***********************************************************")
    }

    protected abstract fun onExecute()
}
