<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/11
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add emp</title>
</head>
<body>
<h2>添加员工</h2>
<hr/>
<div>
    <form action="/emp/add" name="addForm" method="post">
        <p>员工名：<input type="text" name="name"></p>
        <p>入职时间：<input type="date" name="start_date"></p>
        <p>工资：<input type="number" name="salary"></p>
        <p>职称：<input type="text" name="title"></p>
        <p>
            <input type="submit" value="添加员工">
            <input type="reset" value="重置">
        </p>
    </form>
</div>
</body>
</html>
