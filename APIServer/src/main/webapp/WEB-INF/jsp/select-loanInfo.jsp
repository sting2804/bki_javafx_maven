<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sting
  Date: 3/1/15
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
    <title></title>
</head>
<body>
    <p>Users:</p><c:forEach items="${loanInfo}" var="t">
        ${loanInfo}<br>
    </c:forEach>
</body>
</html>
