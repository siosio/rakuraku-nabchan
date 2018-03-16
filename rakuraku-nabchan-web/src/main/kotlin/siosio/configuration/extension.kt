package siosio.configuration

import nablarch.core.exception.*
import nablarch.core.repository.*

fun <T> getRequiredKey(key: String): T {
    return SystemRepository.get<T>(key)
           ?: throw IllegalConfigurationException("required key($key) not found in SystemRepository")
}

fun Map<String, Any>.getInt(key: String, defaultValue: Int): Int {
    return if (containsKey(key)) {
        get(key).toString().toInt()
    } else {
        defaultValue
    }
}