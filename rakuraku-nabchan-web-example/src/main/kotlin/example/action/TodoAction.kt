package example.action

import example.extension.*
import example.todo.*
import nablarch.core.log.*
import nablarch.fw.*
import nablarch.fw.web.*
import nablarch.integration.doma.*

class TodoAction {

    @Transactional
    fun index(request: HttpRequest, context: ExecutionContext): HttpResponse {
        val todoDao = get<TodoDao>()
        context.setRequestScopedVar("todos", todoDao.findAll())
        return HttpResponse("/todo/index.jsp")
    }
    
    fun new(request: HttpRequest, context: ExecutionContext): HttpResponse = HttpResponse("/todo/new.jsp")

    fun create(request: HttpRequest, context: ExecutionContext): HttpResponse {
        return HttpResponse(303, "redirect:///action/todo")
    }
}