package com.semanticversion.gradle.plugin

import java.io.File
import com.semanticversion.gradle.plugin.commons.CommandExecutor
import com.semanticversion.gradle.plugin.commons.ExtendedExecResult

class FakeCommandExecutor: CommandExecutor {
    override fun execute(
        command: String,
        workingDirectory: File?,
        logStandardOutput: Boolean,
        logErrorOutput: Boolean,
        ignoreExitValue: Boolean
    ): ExtendedExecResult {
        TODO("Not yet implemented")
    }
}