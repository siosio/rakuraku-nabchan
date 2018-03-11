package example.component

import nablarch.core.repository.*
import org.flywaydb.core.*
import siosio.configuration.*

class ExampleRepositoryInitializer : RepositoryInitializer {
    override fun initialize() {
        val defaultRepositoryInitializer = DefaultRepositoryInitializer()
        defaultRepositoryInitializer.initialize()

        Flyway().apply {
            dataSource = SystemRepository.get("dataSource")
            migrate()
        }
    }

}