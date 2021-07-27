package com.semanticversion.gradle.plugin

import com.google.common.truth.Truth
import com.semanticversion.Version
import com.semanticversion.VersionIncrementType
import org.junit.Test
import java.io.File

class IncrementVersionHelperTest {

    @Test
    fun `GIVEN a version file WHEN incrementing the major THEN the version is properly incremented`() {
        testIncrement("version = \"1.0.0\"\n", VersionIncrementType.MAJOR, "2.0.0", "version = \"2.0.0\"\n")
        testIncrement("version = \"1.0.0\" \n", VersionIncrementType.MAJOR, "2.0.0", "version = \"2.0.0\" \n")
        testIncrement("version= \"1.0.0\"\n", VersionIncrementType.MAJOR, "2.0.0", "version = \"2.0.0\"\n")
        testIncrement("version =\"1.0.0\"\n", VersionIncrementType.MAJOR, "2.0.0", "version = \"2.0.0\"\n")
        testIncrement("version =\"1.0.0\"\n", VersionIncrementType.MAJOR, "2.0.0", "version = \"2.0.0\"\n")
        testIncrement(" version = \"1.0.0\"\n", VersionIncrementType.MAJOR, "2.0.0", "version = \"2.0.0\"\n")
        testIncrement("version = \"1.0.0\"// This is a comment\n", VersionIncrementType.MAJOR, "2.0.0", "version = \"2.0.0\"// This is a comment\n")
        testIncrement("version = \"10.10.10\"\n", VersionIncrementType.MAJOR, "11.0.0", "version = \"11.0.0\"\n")
        testIncrement("version = \"100.100.100\"\n", VersionIncrementType.MAJOR, "101.0.0", "version = \"101.0.0\"\n")
    }

    @Test
    fun `GIVEN a version file with a classifier WHEN incrementing the major THEN the version is properly incremented`() {
        testIncrement("version = \"1.0.0-SNAPSHOT\"\n", VersionIncrementType.MAJOR, "2.0.0-SNAPSHOT", "version = \"2.0.0-SNAPSHOT\"\n")
    }

    @Test
    fun `GIVEN a version file WHEN incrementing the minor THEN the version is properly incremented`() {
        testIncrement("version = \"1.0.0\"\n", VersionIncrementType.MINOR, "1.1.0", "version = \"1.1.0\"\n")
        testIncrement("version = \"10.10.10\"\n", VersionIncrementType.MINOR, "10.11.0", "version = \"10.11.0\"\n")
        testIncrement("version = \"100.100.100\"\n", VersionIncrementType.MINOR, "100.101.0", "version = \"100.101.0\"\n")
    }

    @Test
    fun `GIVEN a version file WHEN incrementing the patch THEN the version is properly incremented`() {
        testIncrement("version = \"1.0.0\"\n", VersionIncrementType.PATCH, "1.0.1", "version = \"1.0.1\"\n")
        testIncrement("version = \"10.10.10\"\n", VersionIncrementType.PATCH, "10.10.11", "version = \"10.10.11\"\n")
        testIncrement("version = \"100.100.100\"\n", VersionIncrementType.PATCH, "100.100.101", "version = \"100.100.101\"\n")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version file without a version WHEN incrementing the major THEN it throws an exception`() {
        testIncrementFail("// no version found", VersionIncrementType.MAJOR)
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version file with an invalid version WHEN incrementing the major THEN it throws an exception`() {
        testIncrementFail("version = \"abc\"\n", VersionIncrementType.MAJOR)
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version file with the maximum supported version WHEN incrementing the major THEN it throws an exception`() {
        testIncrementFail("version = \"999.999.999\"\n", VersionIncrementType.MAJOR)
    }

    private fun testIncrement(versionFileContent: String, versionIncrementType: VersionIncrementType, expectedVersion: String, expectedContent: String) {
        val versionFile = File.createTempFile("increment", ".tmp")
        versionFile.writeText(versionFileContent)

        val newVersion = IncrementVersionHelper.increment(
            versionFile,
            versionIncrementType,
            null,
            null,
            null,
            FakeCommandExecutor()
        )

        Truth.assertThat(newVersion.toString()).isEqualTo(expectedVersion)
        Truth.assertThat(versionFile.readText()).isEqualTo(expectedContent)
    }

    private fun testIncrementFail(versionFileContent: String, versionIncrementType: VersionIncrementType) {
        val versionFile = File.createTempFile("increment", ".tmp")
        versionFile.writeText(versionFileContent)

        IncrementVersionHelper.increment(
            versionFile,
            versionIncrementType,
            null,
            null,
            null,
            FakeCommandExecutor()
        )
    }
}