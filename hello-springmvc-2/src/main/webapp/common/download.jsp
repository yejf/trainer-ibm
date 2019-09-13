<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/13
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>下载中心</title>
</head>
<body>
  <h3>下载中心</h3>
  <hr/>
  <c:forEach var="file" items="${files}" varStatus="vs">
    <p>${vs.count} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="${path}/download?id=${file.name}">${file.name}</a></p>
  </c:forEach>
</body>
</html>
