package com.semanticversion

import com.semanticversion.common.PropertyResolver

open class SemanticVersionExtension(propertyResolver: PropertyResolver) {

    var versionLocationPath: String = propertyResolver.getRequiredStringProp(::versionLocationPath.name, "./build.gradle")
    var gitHubUserName: String? = propertyResolver.getStringProp(::gitHubUserName.name)
    var gitHubUserEmail: String? = propertyResolver.getStringProp(::gitHubUserEmail.name)
}
