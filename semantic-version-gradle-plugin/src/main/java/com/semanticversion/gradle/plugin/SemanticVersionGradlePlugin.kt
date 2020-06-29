package com.semanticversion.gradle.plugin

import com.semanticversion.SemanticVersionConfig
import com.semanticversion.SemanticVersionExtension
import com.semanticversion.Version
import com.semanticversion.common.GitHelper
import com.semanticversion.gradle.plugin.commons.CommandExecutorImpl
import com.semanticversion.gradle.plugin.commons.GitHelperImpl
import com.semanticversion.gradle.plugin.commons.propertyResolver
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

open class SemanticVersionGradlePlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "semanticVersion"

        fun getExtension(project: Project): SemanticVersionExtension {
            return project.extensions.findByType(SemanticVersionExtension::class.java)!!
        }
    }

    protected lateinit var project: Project
    protected lateinit var gitHelper: GitHelper
    protected lateinit var baseVersion: String
    protected lateinit var extension: SemanticVersionExtension

    override fun apply(project: Project) {
        this.project = project

        gitHelper = GitHelperImpl(project.propertyResolver, CommandExecutorImpl(project, LogLevel.LIFECYCLE))

        if (project.version == Project.DEFAULT_VERSION) {
            project.version = project.rootProject.version
        }

        if (project.version == Project.DEFAULT_VERSION) {
            project.logger.warn("Version not specified on project [" + project.name + "] or its root project. Assigned v0.1.0 as default version")
            project.version = "0.1.0"
        }

        baseVersion = Version(project.version.toString()).baseVersion
        val version = Version(SemanticVersionConfig(project.propertyResolver), gitHelper, baseVersion)
        project.version = version.toString()

        project.subprojects.forEach {
            it.version = version.toString()
        }

        extension = project.extensions.create(EXTENSION_NAME, getExtensionClass(), project.propertyResolver)

        project.tasks.create(IncrementVersionTask.TASK_NAME, IncrementVersionTask::class.java)

        createPrintTask()
    }

    protected open fun getExtensionClass(): Class<out SemanticVersionExtension> {
        return SemanticVersionExtension::class.java
    }

    protected open fun createPrintTask() {
        project.tasks.create(PrintVersionTask.TASK_NAME, PrintVersionTask::class.java)
    }
}
