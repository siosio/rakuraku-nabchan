package siosio.configuration

import nablarch.common.web.interceptor.*
import nablarch.core.date.*
import nablarch.core.repository.*
import nablarch.fw.web.interceptor.*
import nablarch.integration.doma.*
import kotlin.reflect.*

interface RepositoryInitializer {
    fun initialize()

    companion object {
        fun initialize() {
            SystemRepository.load {
                PropertyFileLoader.load("classpath:application.properties")
            }

            val initializer: RepositoryInitializer = SystemRepository.get<String>("repositoryInitializer.className")?.let {
                Class.forName(it).getConstructor().newInstance()
            } as RepositoryInitializer? ?: DefaultRepositoryInitializer()

            initializer.initialize()
        }
    }
}

class DefaultRepositoryInitializer : RepositoryInitializer {
    override fun initialize() {

        SystemRepository.load {
            mapOf(
                    "interceptorsOrder" to listOf(
                            OnErrors::class.qualifiedName,
                            OnError::class.qualifiedName,
                            InjectForm::class.qualifiedName,
                            Transactional::class.qualifiedName
                    ),
                    "systemTimeProvider" to BasicSystemTimeProvider()
            )
        }
        DatabaseConfiguration().configure()
    }

}