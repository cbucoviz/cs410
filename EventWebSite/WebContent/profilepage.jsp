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



var requestData = ["<%=SessionVariables.LOGGED_IN%>", "<%=SessionVariables.USERNAME%>"];
var sessionData = getSessionData(requestData);
	
$('#username_header').text(sessionData["<%=SessionVariables.USERNAME%>"]);


</script>	
	
</head>
<body>

<div class="page_container">
	
	<div class="page_header">
		<h2><b>Profile</b></h2>
	</div>
		
	<div class="page_content" align="center">
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
						Blah
					</td>
				</tr>
				<tr>
					<td style="text-align: right">
						<b>Age:</b>
					</td>
					<td>
						100
					</td>
				</tr>
				<tr>
					<td style="text-align: right">
						<b>Email:</b>
					</td>
					<td>
						blah@blah.com
					</td>
				</tr>
				<tr>
					<td style="text-align: right">
						<b>Location:</b>
					</td>
					<td>
						Hell
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