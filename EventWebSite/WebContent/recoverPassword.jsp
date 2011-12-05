<html>
<head>
<%@ page import="Servlets.SessionVariables" %>
<%@ include file="config/constants.jsp" %>
<link href="config/default.css" rel="stylesheet" type="text/css" />
<link href="config/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="scripts/forwarder.js" type="text/javascript"></script>
	
</head>
<body>

<div class="page_container">
	
	<div class="page_header">
		<h2><b>Recover Your Password</b></h2>
	</div>
		
	<div class="page_content" align="center">
		
		<div id="password_recover_div" align="center">
			<table>
				<tr>
					<td style="text-align: center" colspan="2">
						<h3>Enter your e-mail: </h3>
						<form action="RecoverPassword" method="POST">
							<input type="text" name='email' size="25"/>
							<input type="submit" class="button1" value="Submit"/>
						</form>
					</td>
				</tr>				
			</table>
		</div>
	</div>
	
</div>

<%@ include file="account_update_dialog.jsp" %>

</body>

</html>