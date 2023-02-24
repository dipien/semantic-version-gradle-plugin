package com.semanticversion.gradle.plugin

import com.semanticversion.gradle.plugin.commons.PropertyResolver

open class SemanticVersionExtension(propertyResolver: PropertyResolver) {

    var gitUserName: String? = propertyResolver.getStringProp(::gitUserName.name)
    var gitUserEmail: String? = propertyResolver.getStringProp(::gitUserEmail.name)

}
