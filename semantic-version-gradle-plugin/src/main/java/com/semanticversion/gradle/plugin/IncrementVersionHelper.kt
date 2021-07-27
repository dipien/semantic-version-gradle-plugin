package com.semanticversion.gradle.plugin

import com.jdroid.java.utils.FileUtils
import com.semanticversion.Version
import com.semanticversion.VersionIncrementType
import com.semanticversion.gradle.plugin.commons.CommandExecutor
import java.util.regex.Pattern
import java.io.File

object IncrementVersionHelper {

    fun increment(
        versionFile: File,
        versionIncrementType: VersionIncrementType,
        versionIncrementBranch: String?,
        gitUserName: String?,
        gitUserEmail: String?,
        commandExecutor: CommandExecutor
    ): Version {
        val versionPattern = Pattern.compile("^\\s?version\\s?=\\s?[\"\'](\\d\\d?\\.\\d\\d?\\.\\d\\d?)[\"\']")
        val lines = mutableListOf<String>()
        var newVersion: Version? = null
        FileUtils.readLines(versionFile).forEach { line ->
            if (newVersion == null) {
                val versionMatcher = versionPattern.matcher(line)
                if (versionMatcher.find()) {
                    val versionText = versionMatcher.group(1)
                    newVersion = Version(versionText)
                    versionIncrementType.increment(newVersion!!)
                    val newLineContent = versionMatcher.replaceFirst("""version = "${newVersion!!.baseVersion}"""")
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
                if (gitUserName != null) {
                    commandExecutor.execute("git config user.name $gitUserName")
                }
                if (gitUserEmail != null) {
                    commandExecutor.execute("git config user.email $gitUserEmail")
                }
                commandExecutor.execute("git diff HEAD")
                commandExecutor.execute("git add " + versionFile.absolutePath)
                commandExecutor.execute("git commit --no-gpg-sign -m \"Changed version to v" + Version(newVersion.toString()).baseVersion + "\"")
                commandExecutor.execute("git push origin HEAD:$versionIncrementBranch")
            }
        } else {
            throw RuntimeException("Version not defined on " + versionFile.absolutePath)
        }
        return newVersion!!
    }
}
