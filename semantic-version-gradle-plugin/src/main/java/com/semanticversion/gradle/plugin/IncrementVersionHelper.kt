package com.semanticversion.gradle.plugin

import com.jdroid.java.utils.FileUtils
import com.semanticversion.SemanticVersionConfig
import com.semanticversion.Version
import com.semanticversion.VersionIncrementType
import com.semanticversion.gradle.plugin.commons.GitHelper
import com.semanticversion.gradle.plugin.commons.CommandExecutor
import com.semanticversion.gradle.plugin.commons.propertyResolver
import org.gradle.api.Project
import java.util.regex.Pattern

object IncrementVersionHelper {

    fun increment(
        project: Project,
        versionIncrementType: VersionIncrementType,
        versionIncrementBranch: String?,
        versionLocationPath: String,
        gitUserName: String?,
        gitUserEmail: String?,
        commandExecutor: CommandExecutor
    ) {

        val buildGradleFile = project.file(versionLocationPath)
        // TODO Fix this pattern
        val versionPattern = Pattern.compile("^\\s?version\\s?=\\s?[\"\'](\\d\\d?\\.\\d\\d?\\.\\d\\d?)[\"\']")
        val lines = mutableListOf<String>()
        var versionFound = false
        FileUtils.readLines(buildGradleFile).forEach { line ->
            if (!versionFound) {
                val versionMatcher = versionPattern.matcher(line)
                if (versionMatcher.find()) {
                    val versionText = versionMatcher.group(1)
                    val version = Version(versionText)
                    versionIncrementType.increment(version)
                    val newLineContent = versionMatcher.replaceFirst("""version = "${version.baseVersion}"""")
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
            if (versionIncrementBranch != null) {
                if (gitUserName != null) {
                    commandExecutor.execute("git config user.name $gitUserName")
                }
                if (gitUserEmail != null) {
                    commandExecutor.execute("git config user.email $gitUserEmail")
                }
                commandExecutor.execute("git diff HEAD")
                commandExecutor.execute("git add " + buildGradleFile.absolutePath)
                commandExecutor.execute("git commit --no-gpg-sign -m \"Changed version to v" + Version(project.version.toString()).baseVersion + "\"")
                commandExecutor.execute("git push origin HEAD:$versionIncrementBranch")
            }
        } else {
            throw RuntimeException("Version not defined on " + buildGradleFile.absolutePath)
        }
    }
}
