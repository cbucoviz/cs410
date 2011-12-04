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


<form action="Register" method="POST">
	<div class="page_container">
	
		<div class="page_header">
			<h2><b>Join Now!</b></h2>
		</div>
		
		<div class="page_content" align="center">
		
			<% if(request.getAttribute("emailUsed") == Boolean.TRUE) { %>
					<b><font color="red">Your e-mail is used by another user</font></b><br/>
			<% } %>
			<% if(request.getAttribute("badAge") == Boolean.TRUE) { %>
					<b><font color="red">You need to put in a number for your age</font></b><br/>
			<% } %>
			<% if(request.getAttribute("passwordMatch") == Boolean.FALSE) { %>
					<b><font color="red">Your passwords don't match.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("missingUser") == Boolean.TRUE) { %>
					<b><font color="red">You need to fill in the username.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("missingPassword1") == Boolean.TRUE) { %>
					<b><font color="red">You need to fill in the first password field.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("missingPassword2") == Boolean.TRUE) { %>
					<b><font color="red">You need to fill in the second password field.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("missingEmail") == Boolean.TRUE) { %>
					<b><font color="red">You need to fill in the email field.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("missingAge") == Boolean.TRUE) { %>
					<b><font color="red">You need to fill in the age field.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("missingCity") == Boolean.TRUE) { %>
					<b><font color="red">You need to fill in the city field.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("missingState") == Boolean.TRUE) { %>
					<b><font color="red">You need to fill in the state field.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("missingCountry") == Boolean.TRUE) { %>
					<b><font color="red">You need to fill in the country field.</font></b><br/>
			<% } %>
			<% if(request.getAttribute("userNameExists") == Boolean.TRUE) { %>
					<b><font color="red">A user with such a name already exists.</font></b><br/>
			<% } %>	
			
			<div id="register_content_div" align="center">
				<table id="signup_form_table">
					<tr>
						<td colspan="2" align="center">
							<font color="red"><i>(*) = Mandatory/Required Information</i></font> 
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right"><font color="red">*</font> User Name: </td>
						<td><input 	type="text" 
									name='username' 
									size="25" 
									value="<%= request.getParameter("username") == null ? "" : request.getParameter("username")%>"/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top"><font color="red">*</font> Email Address: </td>
						<td>
							<input type="text" name='useremail' size="25"/>
							<br/>
							<font size="2"><i>(Needed for Account Activation)</i></font>
						</td>
					</tr>
					<tr>
						<td align="right"><font color="red">*</font> Password: </td>
						<td><input type="password" name='password1' size="25"/></td>
					</tr>
					<tr>
						<td align="right"><font color="red">*</font> Confirm Password: </td>
						<td><input type="password" name='password2' size="25"/></td>
					</tr>
					<tr>
						<td align="right">Age: </td>
						<td><input type="text" name='age' size="25"/></td>
					</tr>
					<tr>
						<td align="right"><font color="red">*</font> City: </td>
						<td><input type="text" name='city' size="25"/></td>
					</tr>
					<tr>
						<td align="right">State: </td>
						<td><input type="text" name='state' size="25"/></td>
					</tr>
					<tr>
						<td align="right"><font color="red">*</font> Country: </td>
						<td><input type="text" name='country' size="25"/></td>
					</tr>
					
					<tr>
						<td><br></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
						<input type="submit" class="button1" value="Register"/>
						<a href="home.jsp" class="button1">Return to Main Page</a></td>
					</tr>
				</table>
				<br>
			</div>
		</div>
		
	</div>
</form>
</body>
</html>
