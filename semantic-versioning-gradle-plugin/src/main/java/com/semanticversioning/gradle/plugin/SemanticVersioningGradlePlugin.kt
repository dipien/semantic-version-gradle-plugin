package com.semanticversioning.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class SemanticVersioningGradlePlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "semanticVersioning"
    }

    lateinit var extension: SemanticVersioningGradlePluginExtension
        private set

    override fun apply(project: Project) {
        extension = project.extensions.create(EXTENSION_NAME, SemanticVersioningGradlePluginExtension::class.java, project)

    }
}
