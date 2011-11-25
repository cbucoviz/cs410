<html>
<head>
<%@ page import="Servlets.SessionVariables" %>
<%@ include file="config/constants.jsp" %>
<link href="config/default.css" rel="stylesheet" type="text/css" />
<link href="config/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="scripts/forwarder.js" type="text/javascript"></script>

<script type="text/javascript">
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
		
	<div class="page_content">
		To be Determined
	</div>
	
</div>

</body>
</html>