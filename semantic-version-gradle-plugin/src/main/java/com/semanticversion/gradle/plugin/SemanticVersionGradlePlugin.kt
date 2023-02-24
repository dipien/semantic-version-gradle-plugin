package com.semanticversion.gradle.plugin

import com.semanticversion.SemanticVersionConfig
import com.semanticversion.Version
import com.semanticversion.gradle.plugin.commons.propertyResolver
import com.semanticversion.gradle.plugin.increment.IncrementVersionTask
import com.semanticversion.gradle.plugin.print.PrintVersionTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlin.RuntimeException

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
    protected lateinit var semanticVersionConfig: SemanticVersionConfig

    override fun apply(project: Project) {
        this.project = project

        if (project != project.rootProject) {
            throw RuntimeException("The Semantic Version Gradle plugin must be applied only on the root project")
        }

        if (project.version == Project.DEFAULT_VERSION) {
            throw RuntimeException("Version not specified on root project. Remember to define the version before applying this plugin")
        }

        extension = project.extensions.create(EXTENSION_NAME, getExtensionClass(), project.propertyResolver)

        val propertyResolver = project.propertyResolver
        val snapshot: Boolean? = propertyResolver.getBooleanProp("snapshot")
        val versionClassifier: String? = propertyResolver.getStringProp("versionClassifier")
        val alpha: Boolean? = propertyResolver.getBooleanProp("alpha")
        val beta: Boolean? = propertyResolver.getBooleanProp("beta")
        val rc: Boolean? = propertyResolver.getBooleanProp("rc")
        // The maximum number the MAJOR, MINOR or PATCH version can achieve. If it is not specified,
        // 99 is used for Android projects and 999 for non Android projects
        val maximumMajorVersion: Int? = propertyResolver.getIntegerProp("maximumMajorVersion")
        val maximumMinorVersion: Int? = propertyResolver.getIntegerProp("maximumMinorVersion")
        val maximumPatchVersion: Int? = propertyResolver.getIntegerProp("maximumPatchVersion")

        semanticVersionConfig = SemanticVersionConfig(maximumMajorVersion, maximumMinorVersion, maximumPatchVersion, versionClassifier, snapshot, alpha, beta, rc)

        baseVersion = Version(project.version.toString(), maximumMajorVersion, maximumMinorVersion, maximumPatchVersion).baseVersion
        val version = Version(baseVersion, semanticVersionConfig)
        project.version = version.toString()

        project.subprojects.forEach {
            it.version = version.toString()
        }

        val incrementVersionTask = project.tasks.create(IncrementVersionTask.TASK_NAME, IncrementVersionTask::class.java)
        project.afterEvaluate {
            incrementVersionTask.gitUserName = extension.gitUserName
            incrementVersionTask.gitUserEmail = extension.gitUserEmail
            incrementVersionTask.maximumMajorVersion = maximumMajorVersion
            incrementVersionTask.maximumMinorVersion = maximumMinorVersion
            incrementVersionTask.maximumPatchVersion = maximumPatchVersion
            incrementVersionTask.notCompatibleWithConfigurationCache("Not implemented yet")
        }

        createPrintTask()
    }

    protected open fun getExtensionClass(): Class<out SemanticVersionExtension> {
        return SemanticVersionExtension::class.java
    }

    protected open fun createPrintTask() {
        val printVersionTask = project.tasks.create(PrintVersionTask.TASK_NAME, PrintVersionTask::class.java)
        printVersionTask.notCompatibleWithConfigurationCache("Not implemented yet")
    }
}
