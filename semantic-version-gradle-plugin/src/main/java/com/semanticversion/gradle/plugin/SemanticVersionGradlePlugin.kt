package com.semanticversion.gradle.plugin

import com.semanticversion.Version
import com.semanticversion.common.GitHelper
import com.semanticversion.common.PropertyResolver
import com.semanticversion.gradle.plugin.commons.CommandExecutorImpl
import com.semanticversion.gradle.plugin.commons.GitHelperImpl
import com.semanticversion.gradle.plugin.commons.PropertyResolverImpl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

open class SemanticVersionGradlePlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "semanticVersion"
    }

    protected lateinit var project: Project
    protected lateinit var extension: SemanticVersionGradlePluginExtension
    protected lateinit var propertyResolver: PropertyResolver
    protected lateinit var gitHelper: GitHelper
    protected lateinit var baseVersion: String

    override fun apply(project: Project) {
        this.project = project
        propertyResolver = PropertyResolverImpl(project)
        gitHelper = GitHelperImpl(propertyResolver, CommandExecutorImpl(project, LogLevel.LIFECYCLE))

        if (project.version == Project.DEFAULT_VERSION) {
            project.version = project.rootProject.version
        }

        if (project.version == Project.DEFAULT_VERSION) {
            project.logger.warn("Version not specified on project [" + project.name + "] or its root project. Assigned v0.1.0 as default version")
            project.version = "0.1.0"
        }

        baseVersion = Version(project.version.toString()).baseVersion
        val version = Version(propertyResolver, gitHelper, baseVersion)
        project.version = version.toString()

        project.subprojects.forEach {
            it.version = version.toString()
        }

        extension = project.extensions.create(EXTENSION_NAME, SemanticVersionGradlePluginExtension::class.java)

        project.tasks.create(PrintVersionTask.TASK_NAME, PrintVersionTask::class.java)
        project.tasks.create(IncrementMajorVersionTask.TASK_NAME, IncrementMajorVersionTask::class.java)
        project.tasks.create(IncrementMinorVersionTask.TASK_NAME, IncrementMinorVersionTask::class.java)
        project.tasks.create(IncrementPatchVersionTask.TASK_NAME, IncrementPatchVersionTask::class.java)
    }
}
