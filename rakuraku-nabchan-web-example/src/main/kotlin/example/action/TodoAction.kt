package example.action

import example.extension.*
import example.todo.*
import nablarch.common.web.interceptor.*
import nablarch.core.log.*
import nablarch.fw.*
import nablarch.fw.web.*
import nablarch.integration.doma.*
import java.io.Serializable

class TodoAction {

    @Transactional
    fun index(request: HttpRequest, context: ExecutionContext): HttpResponse {
        val todoDao = get<TodoDao>()
        context.setRequestScopedVar("todos", todoDao.findAll())
        return HttpResponse("/todo/index.html")
    }

    fun new(request: HttpRequest, context: ExecutionContext): HttpResponse = HttpResponse("/todo/new.html")

    @Transactional
    @InjectForm(form = TodoForm::class, prefix = "form")
    fun create(request: HttpRequest, context: ExecutionContext): HttpResponse {
        val todoDao = get<TodoDao>()
        val form: TodoForm = context.getRequestScopedVar("form")
        todoDao.insert(Todo(null, form.title))
        return HttpResponse(303, "redirect:///action/todo")
    }

    class TodoForm : Serializable {
        var title: String? = null
    }
}