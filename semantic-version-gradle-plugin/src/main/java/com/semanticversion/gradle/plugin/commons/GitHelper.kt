package com.semanticversion.gradle.plugin.commons

interface GitHelper {

    fun getGitBranch(): String?

    fun configUserName(userName: String)

    fun configUserEmail(userEmail: String)

    fun diffHead()

    fun add(filePath: String)

    fun commit(message: String)

    fun push(headBranch: String)

    fun pushWithTag(headBranch: String)

    fun tag(tagName: String, message: String)
}
