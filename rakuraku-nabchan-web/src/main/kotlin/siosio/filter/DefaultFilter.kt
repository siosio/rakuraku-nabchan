package siosio.filter

import nablarch.core.repository.*
import nablarch.fw.web.servlet.*
import siosio.configuration.*
import javax.servlet.*

class DefaultFilter : WebFrontController() {

    override fun init(filterConfig: FilterConfig) {
        super.init(filterConfig)
        RepositoryInitializer.initialize()

        val handlerQueueFactory = SystemRepository.get<String>("handlerQueueFactory.className")?.let {
            Class.forName(it).getConstructor().newInstance()
        } as HandlerQueueFactory? ?: DefaultHandlerQueueFactory()
        
        setHandlerQueue(handlerQueueFactory.create(filterConfig))
    }
}