package com.semanticversion.gradle.plugin.common

import com.semanticversion.common.GitHelper

class FakeGitHelper : GitHelper {
    override fun getGitBranch(): String? {
        return null
    }
}
