<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome to Spring Web MVC project</title>
</head>

<body>

<div class="container" style="width: 300px;">
    <c:url value="/j_spring_security_check" var="loginUrl" />
    <sf:form action="${loginUrl}" method="post">
        <h2 class="form-signin-heading">Будьласка, увійдіть у систему</h2>
        <input type="text" class="form-control" name="j_username" placeholder="Login" required autofocus value="">
        <input type="password" class="form-control" name="j_password" placeholder="Password" required value="">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Увійти</button>
    </sf:form>
</div>
</body>
</html>
