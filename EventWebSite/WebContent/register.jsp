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
	<div id="signup_page_div">
	
	<table id="signup_form_table">
	<tr>
	<td colspan="2" align="center">
		<h1>Join Now!</h1>
	</td>
	</tr>
		<tr>
			<td align="right">Name: </td>
			<td><input 	type="text" 
						name='username' 
						size="25" 
						value="<%= request.getParameter("username") == null ? "" : request.getParameter("username")%>"/>
			</td>
		</tr>
		<tr>
			<td align="right">Email Address: </td>
			<td><input type="text" name='useremail' size="25"/></td>
		</tr>
		<tr>
			<td align="right">Password: </td>
			<td><input type="password" name='password1' size="25"/></td>
		</tr>
		<tr>
			<td align="right">Confirm Password: </td>
			<td><input type="password" name='password2' size="25"/></td>
		</tr>
		<tr>
			<td align="right">Default Country: </td>
			<td>
				<select>
		  			<option value="Canada">Canada</option>
				  	<option value="Germany">Germany</option>
				  	<option value="Russia">Russia</option>
				  	<option value="United States">United States</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">Default City/Town: </td>
			<td>
				<select>
		  			<option value="Vancouver">Vancouver</option>
				  	<option value="Berlin">Berlin</option>
				  	<option value="St.Petersburg">St.Petersburg</option>
				  	<option value="New York">New York</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="Register"/></td>
		</tr>
	</table>
	

<br>
<br>
<br>
<br>
<br>
<a href="home.jsp" style="color:yellow"><i><-- Return to main page</i></a>
</div>
</form>
</body>
</html>