package com.semanticversion.gradle.plugin.android

import com.semanticversion.gradle.plugin.common.FakeGitHelper
import com.semanticversion.gradle.plugin.common.FakePropertyResolver
import org.junit.Assert
import org.junit.Test

class AndroidVersionTest {

    @Test
    fun validVersion() {
        val version = createVersion("1.2.3")
        Assert.assertEquals(210010203, version.versionCode)
    }

    private fun createVersion(version: String): AndroidVersion {
        val propertyResolver = FakePropertyResolver(mapOf("MIN_SDK_VERSION" to "21"))
        val gitHelper = FakeGitHelper()
        return AndroidVersion(propertyResolver, gitHelper, version)
    }
}
