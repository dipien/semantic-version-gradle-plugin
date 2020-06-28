package com.semanticversion.common

class FakeGitHelper : GitHelper {
    override fun getGitBranch(): String? {
        return null
    }
}
