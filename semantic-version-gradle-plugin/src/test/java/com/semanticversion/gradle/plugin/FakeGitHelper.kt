package com.semanticversion.gradle.plugin

import com.semanticversion.gradle.plugin.commons.GitHelper

class FakeGitHelper: GitHelper {
    override fun getGitBranch(): String? {
        TODO("Not yet implemented")
    }

    override fun configUserName(userName: String) {
        TODO("Not yet implemented")
    }

    override fun configUserEmail(userEmail: String) {
        TODO("Not yet implemented")
    }

    override fun diffHead() {
        TODO("Not yet implemented")
    }

    override fun add(filePath: String) {
        TODO("Not yet implemented")
    }

    override fun commit(message: String) {
        TODO("Not yet implemented")
    }

    override fun push(headBranch: String) {
        TODO("Not yet implemented")
    }
}