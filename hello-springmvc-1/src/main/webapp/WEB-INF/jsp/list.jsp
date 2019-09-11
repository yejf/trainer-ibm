<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/11
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>emp list</title>
</head>
<body>
<h2>员工列表</h2>
<hr/>
<div>
    <c:forEach var="name" items="${NAMES_KEY}" varStatus="vs">
        <p>${vs.count} -> ${name}</p>
    </c:forEach>
</div>
</body>
</html>
