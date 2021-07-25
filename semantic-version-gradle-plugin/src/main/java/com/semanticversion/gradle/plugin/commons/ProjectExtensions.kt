package com.semanticversion.gradle.plugin.commons

import com.semanticversion.common.PropertyResolver
import org.gradle.api.Project

val Project.propertyResolver: PropertyResolver
    get() = PropertyResolverImpl(this)
