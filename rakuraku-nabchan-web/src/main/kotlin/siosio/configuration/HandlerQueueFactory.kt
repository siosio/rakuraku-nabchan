package siosio.configuration

import nablarch.core.log.*
import nablarch.fw.*
import nablarch.fw.handler.*
import nablarch.fw.web.*
import nablarch.fw.web.handler.*
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
                AccessLogHandler(),
                HttpCharacterEncodingHandler(),
                HttpResponseHandler(),
                HttpErrorHandler(),
                RoutesMapping().apply {
                    basePackage = getRequiredKey("routes.base-package")
                    setMethodBinderFactory(RoutesMethodBinderFactory())
                    initialize()
                }
        )
    }

    class AccessLogHandler() : Handler<HttpRequest, HttpResponse> {
        companion object {
            private val LOG = LoggerManager.get(AccessLogHandler::class.java)
        }

        override fun handle(data: HttpRequest, context: ExecutionContext): HttpResponse {
            val startTime = System.nanoTime()
            val response: HttpResponse = context.handleNext(data)
            LOG.logInfo("path:${data.requestUri}" +
                        "\tstatus_code:${response.statusCode}" +
                        "\texecution_time:${TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)}")
            return response
        }
    }
}
