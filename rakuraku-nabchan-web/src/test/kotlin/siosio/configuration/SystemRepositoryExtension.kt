package siosio.configuration

import nablarch.core.repository.*

fun putSystemRepository(key: String, value: Any) {
    SystemRepository.load({
        mapOf(key to value)
    })
}

