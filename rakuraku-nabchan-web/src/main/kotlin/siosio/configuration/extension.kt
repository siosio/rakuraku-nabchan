package siosio.configuration

import nablarch.core.exception.*
import nablarch.core.repository.*

fun <T> getRequiredKey(key: String): T {
    return SystemRepository.get<T>(key) 
           ?: throw IllegalConfigurationException("required key($key) not found in SystemRepository")
}