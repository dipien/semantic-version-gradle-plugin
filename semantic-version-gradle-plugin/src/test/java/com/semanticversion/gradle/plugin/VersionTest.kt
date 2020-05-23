package com.semanticversion.gradle.plugin

import com.google.common.truth.Truth
import com.semanticversion.gradle.plugin.commons.GitHelper
import com.semanticversion.gradle.plugin.commons.PropertyResolver
import org.junit.Test

class VersionTest {

    @Test
    fun validVersion() {
        var version = createVersion("1.2.3")

        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.toString()).isEqualTo("1.2.3-SNAPSHOT")

        version = createVersion("111.222.333")
        Truth.assertThat(version.versionMajor).isEqualTo(111)
        Truth.assertThat(version.versionMinor).isEqualTo(222)
        Truth.assertThat(version.versionPatch).isEqualTo(333)
        Truth.assertThat(version.toString()).isEqualTo("111.222.333-SNAPSHOT")
    }

    @Test(expected = RuntimeException::class)
    fun notSemanticVersion() {
        createVersion("1.2.3.4")
    }

    @Test(expected = RuntimeException::class)
    fun notSemanticVersion2() {
        createVersion("1.2")
    }

    @Test(expected = RuntimeException::class)
    fun notSemanticVersion3() {
        createVersion("1")
    }

    @Test(expected = RuntimeException::class)
    fun invalidMajor() {
        createVersion("1111.2.3")
    }

    @Test(expected = RuntimeException::class)
    fun invalidMinor() {
        createVersion("1.2222.3")
    }

    @Test(expected = RuntimeException::class)
    fun invalidPatch() {
        createVersion("1.2.3333")
    }

    @Test
    fun incrementMajor() {
        val version = createVersion("1.2.3")
        version.incrementMajor()
        Truth.assertThat(version.versionMajor).isEqualTo(2)
        Truth.assertThat(version.versionMinor).isEqualTo(0)
        Truth.assertThat(version.versionPatch).isEqualTo(0)
    }

    @Test(expected = RuntimeException::class)
    fun invalidIncrementMajor() {
        val version = createVersion("999.2.3")
        version.incrementMajor()
    }

    @Test
    fun incrementMinor() {
        var version = createVersion("1.2.3")
        version.incrementMinor()
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(3)
        Truth.assertThat(version.versionPatch).isEqualTo(0)

        version = createVersion("1.999.3")
        version.incrementMinor()
        Truth.assertThat(version.versionMajor).isEqualTo(2)
        Truth.assertThat(version.versionMinor).isEqualTo(0)
        Truth.assertThat(version.versionPatch).isEqualTo(0)
    }

    @Test(expected = RuntimeException::class)
    fun invalidIncrementMinor() {
        val version = createVersion("999.999.3")
        version.incrementMinor()
    }

    @Test
    fun incrementPatch() {
        var version = createVersion("1.2.3")
        version.incrementPatch()
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(4)

        version = createVersion("1.2.999")
        version.incrementPatch()
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(3)
        Truth.assertThat(version.versionPatch).isEqualTo(0)
    }

    @Test(expected = RuntimeException::class)
    fun invalidIncrementPatch() {
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

        version = Version("111.222.333")
        Truth.assertThat(version.versionMajor).isEqualTo(111)
        Truth.assertThat(version.versionMinor).isEqualTo(222)
        Truth.assertThat(version.versionPatch).isEqualTo(333)
        Truth.assertThat(version.toString()).isEqualTo("111.222.333")
    }

    @Test
    fun `GIVEN a snapshot version WHEN creating a version using the full version`() {
        val version = Version("1.2.3-SNAPSHOT")
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.versionClassifier).isEqualTo("SNAPSHOT")
        Truth.assertThat(version.isSnapshot).isTrue()
        Truth.assertThat(version.toString()).isEqualTo("1.2.3-SNAPSHOT")
    }

    @Test
    fun `GIVEN a non stable version WHEN creating a version using the full version`() {
        val version = Version("1.2.3-BETA")
        Truth.assertThat(version.versionMajor).isEqualTo(1)
        Truth.assertThat(version.versionMinor).isEqualTo(2)
        Truth.assertThat(version.versionPatch).isEqualTo(3)
        Truth.assertThat(version.versionClassifier).isEqualTo("BETA")
        Truth.assertThat(version.isSnapshot).isFalse()
        Truth.assertThat(version.toString()).isEqualTo("1.2.3-BETA")
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

    private fun createVersion(baseVersion: String): Version {
        val gitHelper = object : GitHelper {
            override fun getGitBranch(): String? {
                return null
            }
        }
        val propertyResolver = object : PropertyResolver {
            override fun getStringProp(propertyName: String, defaultValue: String?): String? {
                return defaultValue
            }

            override fun getRequiredStringProp(propertyName: String): String {
                TODO("Not yet implemented")
            }

            override fun getRequiredStringProp(propertyName: String, defaultValue: String): String {
                return defaultValue
            }

            override fun getRequiredBooleanProp(propertyName: String, defaultValue: Boolean): Boolean {
                return defaultValue
            }

            override fun getRequiredIntegerProp(propertyName: String): Int {
                TODO("Not yet implemented")
            }

            override fun getIntegerProp(propertyName: String, defaultValue: Int?): Int? {
                return defaultValue
            }

            override fun getDoubleProp(propertyName: String, defaultValue: Double?): Double? {
                return defaultValue
            }

            override fun getStringListProp(propertyName: String, defaultValue: List<String>?): List<String>? {
                return defaultValue
            }
        }
        return Version(propertyResolver, gitHelper, baseVersion)
    }
}
