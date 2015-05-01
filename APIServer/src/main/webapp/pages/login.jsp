<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>Spring Security</title>
	<link href="<c:url value="/pages/css/styles.css"/>" rel="stylesheet">
	<link href="<c:url value="/pages/css/bootstrap.css" />" rel="stylesheet">

</head>

<body>

<div class="container" style="width: 300px;">
	<c:url value="/j_spring_security_check" var="loginUrl" />
	<form action="${loginUrl}" method="post">
		<input type="text" class="form-control" name="j_username" placeholder="Login" required autofocus value="">
		<input type="password" class="form-control" name="j_password" placeholder="Password" required value="">
		<button class="btn btn-lg btn-primary btn-block" type="submit">Увійти</button>
		<input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
		<label for="remember_me" class="inline">Remember me</label>
	</form>
</div>

</body>
</html>