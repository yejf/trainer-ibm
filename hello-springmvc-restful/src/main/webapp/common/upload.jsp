<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/13
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>upload</title>
</head>
<body>
    <h2>文件上传</h2>
    <hr/>
    <div>
        <form action="${pageContext.request.contextPath}/upload" method="post"
                enctype="multipart/form-data" name="uploadForm">
             选择一个文件：<input type="file" name="uploadFile" accept="image/png">
            <input type="submit" value="上传">
        </form>

        <c:if test="${not empty originalFilename}">
            <p></p>上传的文件名： ${originalFilename}</p>
        </c:if>
        <c:if test="${not empty server_file_name}">
            <p></p>上传后在服务器上生成的文件名： ${server_file_name}</p>
        </c:if>
        <c:if test="${not empty filesize}">
            <p></p>文件大小： ${filesize}</p>
        </c:if>
    </div>
    <p><a href="${pageContext.request.contextPath}/todownload">去下载中心</a></p>
</body>
</html>
