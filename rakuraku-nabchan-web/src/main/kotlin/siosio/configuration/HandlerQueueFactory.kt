package siosio.configuration

import nablarch.common.web.session.*
import nablarch.common.web.session.store.*
import nablarch.core.log.*
import nablarch.fw.*
import nablarch.fw.handler.*
import nablarch.fw.web.*
import nablarch.fw.web.handler.*
import nablarch.fw.web.upload.*
import nablarch.integration.router.*
import java.util.concurrent.*
import javax.servlet.*

interface HandlerQueueFactory {
    fun create(config: FilterConfig): List<Handler<out Any, out Any>>
}

class DefaultHandlerQueueFactory : HandlerQueueFactory {

    override fun create(config: FilterConfig): List<Handler<out Any, out Any>> {

        return listOf(
                GlobalErrorHandler(),
                HttpCharacterEncodingHandler(),
                HttpResponseHandler(),
                MultipartHandler().apply { 
                    setUploadSettings(UploadSettings().apply { 
                        isAutoCleaning = true
                    })
                },
                SessionStoreHandler().apply { 
                    setSessionManager(SessionManager().apply { 
                        setDefaultStoreName("hidden")
                        availableStores = listOf(HiddenStore())
                    })
                },
                HttpErrorHandler(),
                RoutesMapping().apply {
                    basePackage = getRequiredKey("routes.base-package")
                    setMethodBinderFactory(RoutesMethodBinderFactory())
                    initialize()
                }
        )
    }
}
