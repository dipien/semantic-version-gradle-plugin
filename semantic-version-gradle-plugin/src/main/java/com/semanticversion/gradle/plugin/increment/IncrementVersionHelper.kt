package com.semanticversion.gradle.plugin.increment

import com.jdroid.java.utils.FileUtils
import com.semanticversion.Version
import com.semanticversion.VersionIncrementType
import com.semanticversion.gradle.plugin.commons.GitHelper
import org.gradle.api.Project
import java.util.regex.Pattern
import java.io.File

object IncrementVersionHelper {

    fun increment(
        project: Project,
        versionFile: File,
        versionIncrementType: VersionIncrementType,
        versionIncrementBranch: String?,
        includeTag: Boolean?,
        commitMessagePrefix: String?,
        gitUserName: String?,
        gitUserEmail: String?,
        gitHelper: GitHelper
    ): Version {
        val versionPattern = Pattern.compile("""^\s?version\s?=\s?["'](.+)["']""")
        val lines = mutableListOf<String>()
        var newVersion: Version? = null
        FileUtils.readLines(versionFile).forEach { line ->
            if (newVersion == null) {
                val versionMatcher = versionPattern.matcher(line)
                if (versionMatcher.find()) {
                    val versionText = versionMatcher.group(1)
                    newVersion = Version(versionText)
                    versionIncrementType.increment(newVersion!!)
                    val newLineContent = versionMatcher.replaceFirst("""version = "${newVersion!!}"""")
                    lines.add(newLineContent)
                } else {
                    lines.add(line)
                }
            } else {
                lines.add(line)
            }
        }
        if (newVersion != null) {
            FileUtils.writeLines(versionFile, lines)
            if (versionIncrementBranch != null) {
                if (!gitUserName.isNullOrBlank()) {
                    gitHelper.configUserName(gitUserName)
                }
                if (!gitUserEmail.isNullOrBlank()) {
                    gitHelper.configUserEmail(gitUserEmail)
                }
                gitHelper.add(versionFile.absolutePath)
                gitHelper.diffHead()
                gitHelper.commit(commitMessagePrefix.orEmpty() + "Version for project ${project.name} is now v${newVersion.toString()}")

                if (includeTag == true) {
                    gitHelper.tag("${project.name}@${newVersion.toString()}", "Version for project ${project.name} is now v${newVersion.toString()}")
                    gitHelper.pushWithTag(versionIncrementBranch);
                } else {
                    gitHelper.push(versionIncrementBranch)
                }
            }
        } else {
            throw RuntimeException("Version not defined on " + versionFile.absolutePath)
        }
        return newVersion!!
    }
}
