<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="constants.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= WEBSITE_TITLE %></title>
</head>
<body>
<%	
	if(session.getAttribute("loggedIn") == Boolean.TRUE) {%>
	Welcome <%= session.getAttribute("username") %>
<%} else {%>
<a href="register.jsp">Click here to register</a>
<%} %>
</body>
</html>