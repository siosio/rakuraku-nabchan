package siosio.configuration

import nablarch.core.repository.*
import nablarch.core.util.*
import java.util.*

class PropertyFileLoader private constructor(
        private val filePath: String
) : ObjectLoader {
    
    companion object {
        fun load(filePath: String = "classpath:application.properties"): Map<String, Any> {
            return PropertyFileLoader(filePath).load()
        }
    }

    override fun load(): Map<String, Any> {
        return FileUtil.getResource(filePath).use {
            val properties = Properties()
            properties.load(it.bufferedReader())
            properties.map { it.key as String to it.value }
                    .toMap() + loadSystemProperty()
        }
    }

    private fun loadSystemProperty(): Map<String, String> {
        return System.getProperties()
                .map {
                    it.key as String to it.value as String
                }.toMap()
    }
}
