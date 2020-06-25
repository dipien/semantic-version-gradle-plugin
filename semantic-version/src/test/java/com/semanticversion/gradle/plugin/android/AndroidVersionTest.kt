package com.semanticversion.gradle.plugin.android

import com.semanticversion.android.AndroidVersion
import com.semanticversion.gradle.plugin.common.FakeGitHelper
import com.semanticversion.gradle.plugin.common.FakePropertyResolver
import org.junit.Assert
import org.junit.Test

class AndroidVersionTest {

    @Test
    fun `GIVEN a valid base version WHEN creating an AndroidVersion THEN it is successfully created`() {
        val version = createVersion("1.2.3")
        Assert.assertEquals(210010203, version.versionCode)
    }

    @Test
    fun `GIVEN a valid base version & a versionCodePrefix WHEN creating an AndroidVersion THEN it is successfully created`() {
        val version = createVersion("1.2.3", 99)
        Assert.assertEquals(990010203, version.versionCode)
    }

    @Test
    fun `GIVEN a valid base version & a versionCodeExtraBit WHEN creating an AndroidVersion THEN it is successfully created`() {
        val version = createVersion("1.2.3", versionCodeExtraBit = 9)
        Assert.assertEquals(219010203, version.versionCode)
    }

    @Test
    fun `GIVEN a valid base version, a versionCodePrefix & a versionCodeExtraBit WHEN creating an AndroidVersion THEN it is successfully created`() {
        val version = createVersion("1.2.3", versionCodePrefix = 99, versionCodeExtraBit = 9)
        Assert.assertEquals(999010203, version.versionCode)
    }

    @Test
    fun `GIVEN valid version code WHEN creating an AndroidVersion THEN it is successfully created`() {
        val versionCode = 219010203
        val version = AndroidVersion(versionCode)
        Assert.assertEquals(versionCode, version.versionCode)
        Assert.assertEquals("1.2.3", version.toString())
    }

    @Test
    fun `GIVEN valid version code with two digits versions WHEN creating an AndroidVersion THEN it is successfully created`() {
        val versionCode = 210102030
        val version = AndroidVersion(versionCode)
        Assert.assertEquals(versionCode, version.versionCode)
        Assert.assertEquals("10.20.30", version.toString())
    }

    private fun createVersion(version: String, versionCodePrefix: Int? = null, versionCodeExtraBit: Int? = null): AndroidVersion {
        val propertyResolver = FakePropertyResolver(
            mapOf(
                "MIN_SDK_VERSION" to "21",
                "VERSION_CODE_PREFIX" to versionCodePrefix.toString(),
                "VERSION_CODE_EXTRA_BIT" to versionCodeExtraBit.toString()
            )
        )
        val gitHelper = FakeGitHelper()
        return AndroidVersion(propertyResolver, gitHelper, version)
    }
}
