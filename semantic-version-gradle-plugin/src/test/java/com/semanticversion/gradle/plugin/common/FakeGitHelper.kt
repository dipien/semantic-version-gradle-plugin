package com.semanticversion.gradle.plugin.common

import com.semanticversion.gradle.plugin.commons.GitHelper

class FakeGitHelper : GitHelper {
    override fun getGitBranch(): String? {
        return null
    }
}
