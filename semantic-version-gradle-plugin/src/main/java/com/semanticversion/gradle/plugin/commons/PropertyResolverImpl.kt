package com.semanticversion.gradle.plugin.commons

import org.gradle.api.Project

class PropertyResolverImpl(private val project: Project) : PropertyResolver {

    override fun getStringProp(propertyName: String, defaultValue: String?): String? {
        return getProp(propertyName, defaultValue) as String?
    }

    override fun getRequiredStringProp(propertyName: String): String {
        return (getProp(propertyName) ?: throw IllegalStateException("$propertyName is required")) as String
    }

    override fun getRequiredStringProp(propertyName: String, defaultValue: String): String {
        return getProp(propertyName, defaultValue) as String
    }

    override fun getRequiredBooleanProp(propertyName: String, defaultValue: Boolean): Boolean {
        return getProp(propertyName)?.toString()?.toBoolean() ?: defaultValue
    }

    override fun getRequiredIntegerProp(propertyName: String): Int {
        return getIntegerProp(propertyName, null) ?: throw IllegalStateException("$propertyName is required")
    }

    override fun getIntegerProp(propertyName: String, defaultValue: Int?): Int? {
        val value = getProp(propertyName)
        return if (value == null) {
            defaultValue
        } else {
            Integer.parseInt(value.toString())
        }
    }

    override fun getDoubleProp(propertyName: String, defaultValue: Double?): Double? {
        val value = getProp(propertyName)
        return if (value == null || value.toString().isEmpty()) {
            defaultValue
        } else {
            java.lang.Double.parseDouble(value.toString())
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getStringListProp(propertyName: String, defaultValue: List<String>?): List<String>? {
        val value = getProp(propertyName)
        return if (value == null) {
            defaultValue
        } else {
            value as? List<String> ?: value.toString().split(",")
        }
    }

    private fun getProp(propertyName: String, defaultValue: Any? = null): Any? {
        return when {
            project.hasProperty(propertyName) -> project.property(propertyName)
            System.getenv().containsKey(propertyName) -> System.getenv(propertyName)
            else -> defaultValue
        }
    }
}
