package com.semanticversion.gradle.plugin.commons


object GitHelper {

	@JvmStatic
	fun getGitBranch(propertyResolver: PropertyResolver, commandExecutor: CommandExecutor): String? {
		var gitBranch: String = propertyResolver.getStringProp("GIT_BRANCH")
		if (StringUtils.isEmpty(gitBranch)) {
			gitBranch = commandExecutor.execute("git symbolic-ref HEAD").getStandardOutput()
		}
		gitBranch = gitBranch.trim { it <= ' ' }
		gitBranch = gitBranch.replace("origin/", "")
		gitBranch = gitBranch.replace("refs/heads/", "")
		gitBranch = gitBranch.replace("refs/tags/", "")
		return gitBranch
	}
}