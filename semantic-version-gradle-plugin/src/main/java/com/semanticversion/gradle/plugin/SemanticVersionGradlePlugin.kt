package com.semanticversion.gradle.plugin

import com.semanticversion.SemanticVersionConfig
import com.semanticversion.Version
import com.semanticversion.gradle.plugin.commons.propertyResolver
import com.semanticversion.gradle.plugin.increment.IncrementVersionTask
import com.semanticversion.gradle.plugin.print.PrintVersionTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.lang.RuntimeException

open class SemanticVersionGradlePlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "semanticVersion"

        fun getExtension(project: Project): SemanticVersionExtension {
            return project.extensions.findByType(SemanticVersionExtension::class.java)!!
        }
    }

    protected lateinit var project: Project
    protected lateinit var baseVersion: String
    protected lateinit var extension: SemanticVersionExtension

    override fun apply(project: Project) {
        this.project = project

        if (project != project.rootProject) {
            throw RuntimeException("The Semantic Version Gradle plugin must be applied only on the root project")
        }

        // if (project.version == Project.DEFAULT_VERSION) {
        //     project.version = project.rootProject.version
        // }

        if (project.version == Project.DEFAULT_VERSION) {
            project.logger.warn("Version not specified on project [${project.name}] or its root project. Assigned v0.1.0 as default version")
            project.version = "0.1.0"
        }

        extension = project.extensions.create(EXTENSION_NAME, getExtensionClass(), project.propertyResolver)

        baseVersion = Version(project.version.toString()).baseVersion
        val version = Version(baseVersion, SemanticVersionConfig(extension.maximumVersion, extension.versionClassifier, extension.snapshot))
        project.version = version.toString()

        project.subprojects.forEach {
            it.version = version.toString()
        }

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
