package com.semanticversion.gradle.plugin.commons

class GitHelperImpl(private val propertyResolver: PropertyResolver, private val commandExecutor: CommandExecutor) : GitHelper {

    override fun getGitBranch(): String? {
        var gitBranch = propertyResolver.getStringProp("GIT_BRANCH", null)
        if (gitBranch.isNullOrEmpty()) {
            gitBranch = commandExecutor.execute("git symbolic-ref HEAD", logStandardOutput = false).getStandardOutput()
        }
        gitBranch = gitBranch.trim { it <= ' ' }
        gitBranch = gitBranch.replace("origin/", "")
        gitBranch = gitBranch.replace("refs/heads/", "")
        gitBranch = gitBranch.replace("refs/tags/", "")
        return gitBranch
    }
}
