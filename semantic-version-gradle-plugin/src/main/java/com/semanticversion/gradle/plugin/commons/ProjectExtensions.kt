package com.semanticversion.gradle.plugin.commons

import org.gradle.api.Project

val Project.propertyResolver: PropertyResolver
    get() = PropertyResolverImpl(this)
