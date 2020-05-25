package com.semanticversion.gradle.plugin

import com.semanticversion.gradle.plugin.commons.CommandExecutorImpl
import com.semanticversion.gradle.plugin.commons.GitHelperImpl
import com.semanticversion.gradle.plugin.commons.PropertyResolver
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

    override fun apply(project: Project) {
        this.project = project
        extension = project.extensions.create(EXTENSION_NAME, SemanticVersionGradlePluginExtension::class.java, project)

        propertyResolver = PropertyResolverImpl(project)

        if (project.version == Project.DEFAULT_VERSION) {
            project.version = project.rootProject.version
        }

        if (project.version == Project.DEFAULT_VERSION) {
            project.logger.warn("Version not specified on project " + project.name + " or its root project. Assigned v0.1.0 as default version")
            project.version = "0.1.0"
        }

        val baseVersion = Version(project.version.toString()).baseVersion
        val version = createVersion(baseVersion)
        project.version = version.toString()

        project.tasks.create(PrintVersionTask.TASK_NAME, PrintVersionTask::class.java)
        project.tasks.create(IncrementMajorVersionTask.TASK_NAME, IncrementMajorVersionTask::class.java)
        project.tasks.create(IncrementMinorVersionTask.TASK_NAME, IncrementMinorVersionTask::class.java)
        project.tasks.create(IncrementPatchVersionTask.TASK_NAME, IncrementPatchVersionTask::class.java)
    }

    protected fun createVersion(baseVersion: String): Version {
        return Version(propertyResolver, GitHelperImpl(propertyResolver, CommandExecutorImpl(project, LogLevel.LIFECYCLE)), baseVersion)
    }
}
