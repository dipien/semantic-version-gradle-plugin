package com.semanticversion.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class SemanticVersionGradlePlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "semanticVersion"
    }

    lateinit var extension: SemanticVersionGradlePluginExtension
        private set

    override fun apply(project: Project) {
        extension = project.extensions.create(EXTENSION_NAME, SemanticVersionGradlePluginExtension::class.java, project)

        project.tasks.create(PrintVersionTask.TASK_NAME, PrintVersionTask::class.java)
        project.tasks.create(IncrementMajorVersionTask.TASK_NAME, IncrementMajorVersionTask::class.java)
        project.tasks.create(IncrementMinorVersionTask.TASK_NAME, IncrementMinorVersionTask::class.java)
        project.tasks.create(IncrementPatchVersionTask.TASK_NAME, IncrementPatchVersionTask::class.java)
    }
}
