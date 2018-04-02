package example.component

import nablarch.fw.*
import nablarch.fw.web.*
import nablarch.fw.web.handler.*
import siosio.configuration.*
import javax.servlet.*

class ExampleHandlerQueueFactory : DefaultHandlerQueueFactory() {
    override fun create(config: FilterConfig): List<Handler<out Any, out Any>> {
        val queue = ArrayList(super.create(config))
        val index = queue.indexOfFirst { it.javaClass == HttpResponseHandler::class.java }
        if (index != -1) {
            queue.add(index + 1, Handler<HttpRequest, HttpResponse> { data, context ->
                val response: HttpResponse = context.handleNext(data)
//                response.setHeader("Content-Security-Policy", "default-src 'self' fonts.gstatic.com; style-src cdnjs.cloudflare.com fonts.googleapis.com fonts.gstatic.com; img-src *; font-src: fonts.gstatic.com cdnjs.cloudflare.com")
                response
            })
        }
        return queue
    }
}