<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- BEGINNING OF INCLUDES  -->
<link href="config/default.css" rel="stylesheet" type="text/css" />
<script src="scripts/forwarder.js" type="text/javascript"></script>
<!-- END OF INCLUDES -->

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<% if(request.getAttribute("emailUsed") == Boolean.TRUE) { %>
		<b>Your e-mail is used by another user</b><br/>
<% } %>
<% if(request.getAttribute("badAge") == Boolean.TRUE) { %>
		<b>You need to put in a number for your age</b><br/>
<% } %>
<% if(request.getAttribute("passwordMatch") == Boolean.FALSE) { %>
		<b>Your passwords don't match.</b><br/>
<% } %>
<% if(request.getAttribute("missingUser") == Boolean.TRUE) { %>
		<b>You need to fill in the username</b><br/>
<% } %>
<% if(request.getAttribute("missingPassword1") == Boolean.TRUE) { %>
		<b>You need to fill in the first password field</b><br/>
<% } %>
<% if(request.getAttribute("missingPassword2") == Boolean.TRUE) { %>
		<b>You need to fill in the second password field</b><br/>
<% } %>
<% if(request.getAttribute("missingEmail") == Boolean.TRUE) { %>
		<b>You need to fill in the email field</b><br/>
<% } %>
<% if(request.getAttribute("missingAge") == Boolean.TRUE) { %>
		<b>You need to fill in the age field</b><br/>
<% } %>
<% if(request.getAttribute("missingCity") == Boolean.TRUE) { %>
		<b>You need to fill in the city field</b><br/>
<% } %>
<% if(request.getAttribute("missingState") == Boolean.TRUE) { %>
		<b>You need to fill in the state field</b><br/>
<% } %>
<% if(request.getAttribute("missingCountry") == Boolean.TRUE) { %>
		<b>You need to fill in the country field</b><br/>
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
				<td align="right">User Name: </td>
				<td><input 	type="text" 
							name='username' 
							size="25" 
							value="<%= request.getParameter("username") == null ? "" : request.getParameter("username")%>"/>
				</td>
			</tr>
			<tr>
				<td align="right">Full Name: </td>
				<td><input type="text" name='fullname' size="25"/>
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
				<td align="right">Age: </td>
				<td><input type="text" name='age' size="25"/></td>
			</tr>
			<tr>
				<td align="right">City: </td>
				<td><input type="text" name='city' size="25"/></td>
			</tr>
			<tr>
				<td align="right">State: </td>
				<td><input type="text" name='state' size="25"/></td>
			</tr>
			<tr>
				<td align="right">Country: </td>
				<td><input type="text" name='country' size="25"/></td>
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
