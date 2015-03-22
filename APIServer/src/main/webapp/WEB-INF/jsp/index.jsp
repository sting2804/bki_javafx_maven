<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome to Spring Web MVC project</title>
</head>

<body>

<p>Hello! This is the default welcome page for a Spring Web MVC project.</p>

<p><i>To display a different welcome page for this project, modify</i>
    <tt>index.jsp</tt> <i>, or create your own welcome page then change
        the redirection in</i> <tt>redirect.jsp</tt> <i>to point to the new
        welcome page and also update the welcome-file setting in</i>
    <tt>web.xml</tt>.</p>

<%--<sf:form action="/select/client" name="selectClient" method="GET">--%>
    <%--<input name="submit" value="submit GET" type="submit"/>--%>
<%--</sf:form>--%>

<%--<sf:form action="/select/client" name="Client" method="POST">--%>
    <%--<input name="submit" value="submit POST" type="submit"/>--%>
<%--</sf:form>--%>

<%--<br>--%>

<%--<sf:form action="/select/loanInfo" name="selectLoanInfo" method="GET">--%>
    <%--<input name="submit" value="loan info" type="submit"/>--%>
<%--</sf:form>--%>
</body>
</html>
