package com.semanticversion.android

import com.semanticversion.SemanticVersionConfig
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
    fun `GIVEN a valid base version, with minSdkVersionAsVersionCodePrefix disableed WHEN creating an AndroidVersion THEN it is successfully created`() {
        val version = createVersion("1.2.3", minSdkVersionAsVersionCodePrefix = false)
        Assert.assertEquals(10203, version.versionCode)
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

    @Test
    fun `GIVEN valid version code with only 1 prefix WHEN creating an AndroidVersion THEN it is successfully created`() {
        val versionCode = 1100203
        val version = AndroidVersion(versionCode)
        Assert.assertEquals(versionCode, version.versionCode)
        Assert.assertEquals("10.2.3", version.toString())
    }

    @Test
    fun `GIVEN valid version code without prefixes and two digits major WHEN creating an AndroidVersion THEN it is successfully created`() {
        val versionCode = 100203
        val version = AndroidVersion(versionCode)
        Assert.assertEquals(versionCode, version.versionCode)
        Assert.assertEquals("10.2.3", version.toString())
    }

    @Test
    fun `GIVEN valid version code without prefixes WHEN creating an AndroidVersion THEN it is successfully created`() {
        val versionCode = 10203
        val version = AndroidVersion(versionCode)
        Assert.assertEquals(versionCode, version.versionCode)
        Assert.assertEquals("1.2.3", version.toString())
    }

    @Test
    fun `GIVEN valid version code without major without prefixes WHEN creating an AndroidVersion THEN it is successfully created`() {
        val versionCode = 203
        val version = AndroidVersion(versionCode)
        Assert.assertEquals(versionCode, version.versionCode)
        Assert.assertEquals("0.2.3", version.toString())
    }

    @Test
    fun `GIVEN valid version code without minor prefixes WHEN creating an AndroidVersion THEN it is successfully created`() {
        val versionCode = 3
        val version = AndroidVersion(versionCode)
        Assert.assertEquals(versionCode, version.versionCode)
        Assert.assertEquals("0.0.3", version.toString())
    }

    private fun createVersion(version: String, versionCodePrefix: Int? = null, versionCodeExtraBit: Int = 0, minSdkVersionAsVersionCodePrefix: Boolean = true): AndroidVersion {
        return AndroidVersion(version, versionCodePrefix, minSdkVersionAsVersionCodePrefix, versionCodeExtraBit, SemanticVersionConfig(), 21)
    }
}
