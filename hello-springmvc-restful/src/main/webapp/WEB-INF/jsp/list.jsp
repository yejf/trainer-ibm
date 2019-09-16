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
    <hr/>
    <table style="width; 70%">
        <tr>
            <th>序号</th>
            <th>员工名</th>
            <th>入职时间</th>
            <th>工资</th>
            <th>职称</th>
        </tr>
        <c:if test="${not empty EMPS_LIST_KEY}">
            <c:forEach var="e" items="${EMPS_LIST_KEY}" varStatus="vs">
                <tr>
                    <td>${vs.count}</td>
                    <td>${e.name}</td>
                    <td>${e.start_date}</td>
                    <td>${e.salary}</td>
                    <td>${e.title}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
</body>
</html>
