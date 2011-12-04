<html>
<head>
<%@ page import="Servlets.SessionVariables" %>
<%@ include file="config/constants.jsp" %>
<link href="config/default.css" rel="stylesheet" type="text/css" />
<link href="config/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="scripts/forwarder.js" type="text/javascript"></script>

<script type="text/javascript">

//hook in the account info update dialog
$("button[name=account_change_button]").click(function()
	{
		$('#account_info_form').dialog('open');
	}
);



var requestData = ["<%=SessionVariables.LOGGED_IN%>", "<%=SessionVariables.USERNAME%>", "<%=SessionVariables.EMAIL%>"];
var sessionData = getSessionData(requestData);
	
$('#profile_username').text(sessionData["<%=SessionVariables.USERNAME%>"]);
$('#profile_email').text(sessionData["<%=SessionVariables.EMAIL%>"]);


</script>	
	
</head>
<body>

<div class="page_container">
	
	<div class="page_header">
		<h2><b>Profile</b></h2>
	</div>
		
	<div class="page_content" align="center">
		
		<% if(request.getAttribute("oldPasswordMatch") == Boolean.FALSE) { %>
		<b><font color="red">Your old password is incorrect.</font></b><br/>
		<% } %>
		
		<% if(request.getAttribute("newPasswordMatch") == Boolean.FALSE) { %>
		<b><font color="red">Your new passwords do not match.</font></b><br/>
		<% } %>
		
		<% if(request.getAttribute("missingOldPassword") == Boolean.TRUE) { %>
		<b><font color="red">You need to type in your old password in the 'Old Password' field.</font></b><br/>
		<% } %>
		
		<% if(request.getAttribute("missingNewPassword1") == Boolean.TRUE) { %>
		<b><font color="red">You need to type in a new password in the 'New Password' field.</font></b><br/>
		<% } %>
		
		<% if(request.getAttribute("missingNewPassword2") == Boolean.TRUE) { %>
		<b><font color="red">You need to type in your new password again in the 'Confirm New Password' field.</font></b><br/>
		<% } %>
		
		<% if(request.getAttribute("updateError") == Boolean.TRUE) { %>
		<b><font color="red">Problem updateing new password. Please try again.</font></b><br/>
		<% } %>
		
		<br/>
		
		<div id="account_info_div" align="center">
			<table>
				<tr>
					<td style="text-align: center" colspan="2">
						<i><h3>Account Info</h3></i>
					</td>
				</tr>
				<tr>
					<td style="text-align: right">
						<b>Name:</b>
					</td>
					<td>
						<h3 id="profile_username"></h3>
					</td>
				</tr>
				<tr>
					<td style="text-align: right">
						<b>Email:</b>
					</td>
					<td>
						<h3 id="profile_email"></h3>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<br/>
						<button type="button" name="account_change_button" class="button1" value="account_change">Change Info</button>
					</td>
				</tr>
				
			</table>
		</div>
	</div>
	
</div>

<%@ include file="account_update_dialog.jsp" %>

</body>

</html>