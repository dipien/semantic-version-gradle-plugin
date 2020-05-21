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

    }
}
