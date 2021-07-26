package com.semanticversion

import com.google.common.truth.Truth
import com.semanticversion.common.FakeGitHelper
import com.semanticversion.common.FakePropertyResolver
import org.junit.Test

class VersionTest {

    @Test
    fun `GIVEN a valid snapshot version WHEN creating a version THEN it is successfully created`() {
        var version = createVersion("1.2.3")

        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.isSnapshot).isTrue()
        Truth.assertThat(version.isLocal).isFalse()
        Truth.assertThat(version.isVersionTimestampEnabled).isFalse()
        Truth.assertThat(version.featureName).isNull()
        Truth.assertThat(version.versionClassifier).isEqualTo("SNAPSHOT")
        Truth.assertThat(version.baseVersion).isEqualTo("1.2.3")
        Truth.assertThat(version.toString()).isEqualTo("1.2.3-SNAPSHOT")

        version = createVersion("111.222.333")
        Truth.assertThat(version.versionMajor).isEqualTo(111)
        Truth.assertThat(version.versionMinor).isEqualTo(222)
        Truth.assertThat(version.versionPatch).isEqualTo(333)
        Truth.assertThat(version.isSnapshot).isTrue()
        Truth.assertThat(version.isLocal).isFalse()
        Truth.assertThat(version.isVersionTimestampEnabled).isFalse()
        Truth.assertThat(version.featureName).isNull()
        Truth.assertThat(version.versionClassifier).isEqualTo("SNAPSHOT")
        Truth.assertThat(version.baseVersion).isEqualTo("111.222.333")
        Truth.assertThat(version.toString()).isEqualTo("111.222.333-SNAPSHOT")
    }

    @Test
    fun `GIVEN a valid non snapshot version WHEN creating a version THEN it is successfully created`() {
        val version = createVersion("1.2.3", snapshot = false)

        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.isSnapshot).isFalse()
        Truth.assertThat(version.isLocal).isFalse()
        Truth.assertThat(version.isVersionTimestampEnabled).isFalse()
        Truth.assertThat(version.featureName).isNull()
        Truth.assertThat(version.versionClassifier).isNull()
        Truth.assertThat(version.baseVersion).isEqualTo("1.2.3")
        Truth.assertThat(version.toString()).isEqualTo("1.2.3")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN an invalid version WHEN creating a version THEN it throws an exception`() {
        createVersion("1.2.1234")
    }

    @Test
    fun `GIVEN a valid version WHEN creating a version using the splits constructor THEN it is successfully created`() {
        val version = Version(1, 2, 3)

        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.isSnapshot).isTrue()
        Truth.assertThat(version.isLocal).isFalse()
        Truth.assertThat(version.isVersionTimestampEnabled).isFalse()
        Truth.assertThat(version.featureName).isNull()
        Truth.assertThat(version.versionClassifier).isNull()
        Truth.assertThat(version.baseVersion).isEqualTo("1.2.3")
        Truth.assertThat(version.toString()).isEqualTo("1.2.3")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN an invalid version WHEN creating a version using the splits constructor THEN it throws an exception`() {
        Version(1, 2, 1234)
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with 4 segments WHEN creating a version THEN it throws an exception`() {
        createVersion("1.2.3.4")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with 4 segments WHEN creating a version using the version constructor THEN it throws an exception`() {
        Version("1.2.3.4")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with 2 segments WHEN creating a version THEN it throws an exception`() {
        createVersion("1.2")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with 2 segments WHEN creating a version using the version constructor THEN it throws an exception`() {
        Version("1.2")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with 1 segments WHEN creating a version THEN it throws an exception`() {
        createVersion("1")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with 1 segments WHEN creating a version using the version constructor THEN it throws an exception`() {
        Version("1")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with invalid major WHEN creating a version THEN it throws an exception`() {
        createVersion("1111.2.3")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with invalid major WHEN creating a version using the version constructor THEN it throws an exception`() {
        Version("1111.2.3")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with invalid minor WHEN creating a version THEN it throws an exception`() {
        createVersion("1.2222.3")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with invalid minor WHEN creating a version using the version constructor THEN it throws an exception`() {
        Version("1.2222.3")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with invalid patch WHEN creating a version THEN it throws an exception`() {
        createVersion("1.2.3333")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with invalid patch WHEN creating a version using the version constructor THEN it throws an exception`() {
        Version("1.2.3333")
    }

    @Test
    fun `GIVEN a version WHEN creating invoking incrementMajor() THEN it increments major and reset minor and patch`() {
        val version = createVersion("1.2.3")
        version.incrementMajor()
        Truth.assertThat(version.versionMajor).isEqualTo(2)
        Truth.assertThat(version.versionMinor).isEqualTo(0)
        Truth.assertThat(version.versionPatch).isEqualTo(0)
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with the maximum possible major WHEN creating invoking incrementMajor() THEN it throws an exception`() {
        val version = createVersion("999.2.3")
        version.incrementMajor()
    }

    @Test
    fun `GIVEN a version WHEN creating invoking incrementMinor() THEN it increments minor and reset the patch`() {
        val version = createVersion("1.2.3")
        version.incrementMinor()
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(3)
        Truth.assertThat(version.versionPatch).isEqualTo(0)
    }

    @Test
    fun `GIVEN a version with the maximum minor WHEN creating invoking incrementMinor() THEN it increments major and reset minor & patch`() {
        val version = createVersion("1.999.3")
        version.incrementMinor()
        Truth.assertThat(version.versionMajor).isEqualTo(2)
        Truth.assertThat(version.versionMinor).isEqualTo(0)
        Truth.assertThat(version.versionPatch).isEqualTo(0)
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with the maximum possible major & minor WHEN creating invoking incrementMinor() THEN it throws an exception`() {
        val version = createVersion("999.999.3")
        version.incrementMinor()
    }

    @Test
    fun `GIVEN a version WHEN creating invoking incrementPatch() THEN it increments the patch`() {
        val version = createVersion("1.2.3")
        version.incrementPatch()
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(4)
    }

    @Test
    fun `GIVEN a version with the maximum patch WHEN creating invoking incrementPatch() THEN it increments minor and reset patch`() {
        val version = createVersion("1.2.999")
        version.incrementPatch()
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(3)
        Truth.assertThat(version.versionPatch).isEqualTo(0)
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with the maximum possible major, minor & patch WHEN creating invoking incrementPatch() THEN it throws an exception`() {
        val version = createVersion("999.999.999")
        version.incrementPatch()
    }

    @Test
    fun `GIVEN a stable version WHEN creating a version using the full version`() {
        var version = Version("1.2.3")
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.toString()).isEqualTo("1.2.3")
        Truth.assertThat(version.isSnapshot).isFalse()
        Truth.assertThat(version.isLocal).isFalse()
        Truth.assertThat(version.isVersionTimestampEnabled).isFalse()

        version = Version("111.222.333")
        Truth.assertThat(version.versionMajor).isEqualTo(111)
        Truth.assertThat(version.versionMinor).isEqualTo(222)
        Truth.assertThat(version.versionPatch).isEqualTo(333)
        Truth.assertThat(version.toString()).isEqualTo("111.222.333")
        Truth.assertThat(version.isSnapshot).isFalse()
        Truth.assertThat(version.isLocal).isFalse()
        Truth.assertThat(version.isVersionTimestampEnabled).isFalse()
    }

    @Test
    fun `GIVEN a snapshot version WHEN creating a version using the full version`() {
        val version = Version("1.2.3-SNAPSHOT")
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.toString()).isEqualTo("1.2.3-SNAPSHOT")
        Truth.assertThat(version.versionClassifier).isEqualTo("SNAPSHOT")
        Truth.assertThat(version.isSnapshot).isTrue()
        Truth.assertThat(version.isLocal).isFalse()
        Truth.assertThat(version.isVersionTimestampEnabled).isFalse()
    }

    @Test
    fun `GIVEN a beta version WHEN creating a version using the full version`() {
        val version = Version("1.2.3-BETA")
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.toString()).isEqualTo("1.2.3-BETA")
        Truth.assertThat(version.versionClassifier).isEqualTo("BETA")
        Truth.assertThat(version.isSnapshot).isFalse()
        Truth.assertThat(version.isLocal).isFalse()
        Truth.assertThat(version.isVersionTimestampEnabled).isFalse()
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with four segments WHEN creating a version using the full version THEN an exception is thrown`() {
        Version("1.2.3.4")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with two segments WHEN creating a version using the full version THEN an exception is thrown`() {
        createVersion("1.2")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with one segment WHEN creating a version using the full version THEN an exception is thrown`() {
        createVersion("1")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with an invalid major WHEN creating a version using the full version THEN an exception is thrown`() {
        createVersion("1111.2.3")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with an invalid minor WHEN creating a version using the full version THEN an exception is thrown`() {
        createVersion("1.2222.3")
    }

    @Test(expected = RuntimeException::class)
    fun `GIVEN a version with an invalid patch WHEN creating a version using the full version THEN an exception is thrown`() {
        createVersion("1.2.3333")
    }

    private fun createVersion(baseVersion: String, snapshot: Boolean = true): Version {
        val propertyResolver = FakePropertyResolver()
        val gitHelper = FakeGitHelper()
        val semanticVersionConfig = SemanticVersionConfig(propertyResolver)
        semanticVersionConfig.snapshot = snapshot
        return Version(baseVersion, semanticVersionConfig, gitHelper)
    }
}
