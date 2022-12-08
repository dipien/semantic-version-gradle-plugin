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

    override fun configUserName(userName: String) {
        commandExecutor.execute("git config user.name $userName")
    }

    override fun configUserEmail(userEmail: String) {
        commandExecutor.execute("git config user.email $userEmail")
    }

    override fun diffHead() {
        commandExecutor.execute("git diff HEAD")
    }

    override fun commit(message: String) {
        commandExecutor.execute("git commit -m \"$message\"")
    }

    override fun push(headBranch: String) {
        commandExecutor.execute("git push origin HEAD:$headBranch")
    }

    override fun pushWithTag(headBranch: String) {
        commandExecutor.execute("git push --follow-tags origin HEAD:$headBranch")
    }

    override fun tag(tagName: String, message: String) {
        commandExecutor.execute("git tag -a \"$tagName\" -m \"$message\"")
    }

    override fun add(filePath: String) {
        commandExecutor.execute("git add $filePath")
    }

}
