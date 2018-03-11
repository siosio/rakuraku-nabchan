@file:JvmName(name = "WebApplication")
package siosio

import org.apache.catalina.*
import org.apache.catalina.core.*
import org.apache.catalina.startup.*
import org.apache.catalina.valves.*
import org.apache.tomcat.util.descriptor.web.*
import org.slf4j.*
import org.slf4j.bridge.*
import siosio.configuration.*
import siosio.filter.*
import java.io.*


object WebApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        val configuration = PropertyFileLoader.load()
        val tomcat = Tomcat()
        tomcat.setPort(configuration.getOrDefault("port", "8080").toString().toInt())
        val webapp = tomcat.addWebapp(configuration.getOrDefault("contextPath", "/").toString(), File(".").absolutePath)
        
        webapp.useHttpOnly = true
        webapp.sessionTimeout = 30
        addFilter(webapp)
        tomcat.connector

        tomcat.start()
        tomcat.server.await()
    }

    private fun addFilter(webapp: Context) {
        val filterDef = FilterDef()
        filterDef.filterName = "default-servlet"
        filterDef.filter = DefaultFilter()
        webapp.addFilterDef(filterDef)

        val filterMap = FilterMap()
        filterMap.filterName = filterDef.filterName
        filterMap.addURLPattern("/")
        filterMap.addURLPattern("/action/*")
        webapp.addFilterMap(filterMap)
    }
    
    class Slf4jAccessLogValve: AccessLogValve() {
        private val log: Logger = LoggerFactory.getLogger("access")
        override fun log(message: CharArrayWriter) {
            println("message = ${message}")
            val writer = StringWriter()
            message.writeTo(writer)
            writer.flush()
            log.info(writer.toString())
        }
    }
}