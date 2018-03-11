<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>TODOリスト</title>
</head>
<body>
<h1>TODO一覧</h1>
<div>
  <a href='<c:url value="/action/todo/new" />'>登録</a>
</div>
<ul>
  <%--@elvariable id="todos" type="java.util.List<example.todo.Todo>"--%>
  <c:forEach items="${todos}" var="todo">
    <li><c:out value="${todo.title}" /></li>
  </c:forEach>
</ul>
</body>
</html>
