package com.semanticversion

import com.semanticversion.common.PropertyResolver

open class SemanticVersionExtension(propertyResolver: PropertyResolver) {

    var versionLocationPath: String = propertyResolver.getRequiredStringProp(::versionLocationPath.name, "./build.gradle")
    var gitUserName: String? = propertyResolver.getStringProp(::gitUserName.name)
    var gitEmail: String? = propertyResolver.getStringProp(::gitEmail.name)
}
