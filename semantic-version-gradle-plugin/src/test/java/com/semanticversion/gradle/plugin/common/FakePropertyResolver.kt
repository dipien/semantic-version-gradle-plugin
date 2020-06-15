package com.semanticversion.gradle.plugin.common

import com.semanticversion.gradle.plugin.commons.PropertyResolver

class FakePropertyResolver : PropertyResolver {
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

    override fun getRequiredIntegerProp(propertyName: String, defaultValue: Int): Int {
        return defaultValue
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
