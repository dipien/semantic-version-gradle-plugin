package com.semanticversion.gradle.plugin

import java.io.File
import com.semanticversion.gradle.plugin.commons.CommandExecutor

class FakeCommandExecutor: CommandExecutor {
    override fun execute(
        command: String,
        workingDirectory: File?,
        logStandardOutput: Boolean,
        logErrorOutput: Boolean,
        ignoreExitValue: Boolean
    ): com.semanticversion.gradle.plugin.commons.ExtendedExecResult {
        TODO("Not yet implemented")
    }
}