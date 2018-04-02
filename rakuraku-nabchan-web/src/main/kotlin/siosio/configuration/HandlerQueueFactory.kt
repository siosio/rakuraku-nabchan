package siosio.configuration

import nablarch.common.web.session.*
import nablarch.common.web.session.store.*
import nablarch.fw.*
import nablarch.fw.handler.*
import nablarch.fw.web.handler.*
import nablarch.fw.web.handler.responsewriter.thymeleaf.*
import nablarch.fw.web.upload.*
import nablarch.integration.router.*
import org.thymeleaf.*
import org.thymeleaf.templateresolver.*
import javax.servlet.*

interface HandlerQueueFactory {
    fun create(config: FilterConfig): List<Handler<out Any, out Any>>
}

open class DefaultHandlerQueueFactory : HandlerQueueFactory {

    override fun create(config: FilterConfig): List<Handler<out Any, out Any>> {

        return listOf(
                GlobalErrorHandler(),
                HttpCharacterEncodingHandler().apply { 
                    setAppendResponseCharacterEncoding(true)
                },
                HttpResponseHandler().apply {
                    setCustomResponseWriter(ThymeleafResponseWriter().apply {
                        setTemplateEngine(TemplateEngine().apply {
                            setTemplateResolver(ClassLoaderTemplateResolver(config.javaClass.classLoader).apply { 
                                prefix = "/templates"
                                suffix = "html"
                                characterEncoding = "utf-8"
                                isCacheable = true
                            })
                        })
                    })
                },
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
