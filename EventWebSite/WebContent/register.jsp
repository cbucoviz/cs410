<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- BEGINNING OF INCLUDES  -->
<%@ include file="config/constants.jsp" %>
<link href="config/default.css" rel="stylesheet" type="text/css" />
<script src="plugins/jquery.js" type="text/javascript"></script>
<script src="scripts/forwarder.js" type="text/javascript"></script>
<!-- END OF INCLUDES -->

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= WEBSITE_TITLE %></title>
</head>
<body>

<% if(request.getAttribute("passwordMatch") == Boolean.FALSE) { %>
		<b>Your passwords don't match.</b>
<% } %>

<form action="Register" method="POST">
Name: <input 	type="text" 
				name='username' 
				size="25" 
				value="<%= request.getParameter("username") == null ? "" : request.getParameter("username")%>" 
				/>
				
<br/>
Password: <input type="password" name='password1' size="25"/><br/>
Confirm Password: <input type="password" name='password2' size="25"/>
<input type="submit" value="OK"/>
</form>
</body>
</html>