package com.semanticversion.gradle.plugin

import com.jdroid.java.utils.FileUtils
import com.semanticversion.SemanticVersionConfig
import com.semanticversion.Version
import com.semanticversion.gradle.plugin.commons.AbstractTask
import com.semanticversion.gradle.plugin.commons.propertyResolver
import java.util.regex.Pattern

abstract class AbstractIncrementVersionTask : AbstractTask() {

    override fun onExecute() {
        val buildGradleFile = project.file(project.propertyResolver.getRequiredStringProp("VERSION_LOCATION_FILE", "./build.gradle"))
        val versionPattern = Pattern.compile("^\\s?version\\s?=\\s?[\"\'](\\d\\d?\\.\\d\\d?\\.\\d\\d?)[\"\']")
        val lines = mutableListOf<String>()
        var versionFound = false
        FileUtils.readLines(buildGradleFile).forEach { line ->
            if (!versionFound) {
                val versionMatcher = versionPattern.matcher(line)
                if (versionMatcher.find()) {
                    val versionText = versionMatcher.group(1)
                    val version = Version(SemanticVersionConfig(project.propertyResolver), gitHelper, versionText)
                    incrementVersion(version)
                    val newLineContent = versionMatcher.replaceFirst("version = '" + version.baseVersion + "'")
                    lines.add(newLineContent)
                    project.version = version.toString()
                    versionFound = true
                } else {
                    lines.add(line)
                }
            } else {
                lines.add(line)
            }
        }
        if (versionFound) {
            FileUtils.writeLines(buildGradleFile, lines)
            val ciGithubUserName = project.propertyResolver.getStringProp("CI_GITHUB_USER_NAME")
            if (ciGithubUserName != null) {
                commandExecutor.execute("git config user.name $ciGithubUserName")
            }
            val ciGithubUserEmail = project.propertyResolver.getStringProp("CI_GITHUB_USER_EMAIL")
            if (ciGithubUserEmail != null) {
                commandExecutor.execute("git config user.email $ciGithubUserEmail")
            }
            commandExecutor.execute("git diff HEAD")
            commandExecutor.execute("git add " + buildGradleFile.absolutePath)
            commandExecutor.execute("git commit --no-gpg-sign -m \"Changed version to v" + Version(project.version.toString()).baseVersion + "\"")
            val versionIncrementPushEnabled: Boolean = project.propertyResolver.getRequiredBooleanProp("VERSION_INCREMENT_PUSH_ENABLED", true)
            if (versionIncrementPushEnabled) {
                val versionIncrementBranch = project.propertyResolver.getStringProp("VERSION_INCREMENT_BRANCH")
                if (versionIncrementBranch != null) {
                    commandExecutor.execute("git push origin HEAD:$versionIncrementBranch")
                } else {
                    commandExecutor.execute("git reset --soft HEAD~1")
                    commandExecutor.execute("git add .")
                    commandExecutor.execute("git stash")
                    throw RuntimeException("Missing VERSION_INCREMENT_BRANCH property. Reverting commit.")
                }
            }
        } else {
            throw RuntimeException("Version not defined on " + buildGradleFile.absolutePath)
        }
    }

    protected abstract fun incrementVersion(version: Version)
}
