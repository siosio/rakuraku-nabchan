<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>TODO追加</title>
</head>
<body>
<h1>TODO追加</h1>

<form method="post" action="<c:url value="/action/todo/new" />">
  <input type="text" name="form.title" />
  <button type="submit">登録</button>
</form>
</body>
</html>
