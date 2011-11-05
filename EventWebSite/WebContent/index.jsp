<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- BEGINNING OF INCLUDES  -->
<%@ include file="config/constants.jsp" %>
<link href="config/default.css" rel="stylesheet" type="text/css" />
<script src="plugins/jquery.js" type="text/javascript"></script>
<script src="plugins/marquee.js" type="text/javascript"></script>
<script src="config/ticker_config.js" type="text/javascript"></script>
<script src="scripts/forwarder.js" type="text/javascript"></script>
<!-- END OF INCLUDES -->

<script type="text/javascript">

function update()
{
	setTimeout("update()",1000);
}


</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= WEBSITE_TITLE %></title>

</head>
<body onload="update()">

<table border="0" cellpadding="0" cellspacing="5">
<tr>
	<td valign="top" width="300px">
	<!-- Ka Ho's side bar should replace this stuff -->
	<a href="home.jsp">Home</a><br/>
	<a href="register.jsp">Register</a><br/>
	About<br/>
	</td>
	
	<td align="center">
	<div id="mainContent" class="scrollFrame">
		<%@ include file="home.jsp" %>
	</div>
	</td>
</tr>
<tr>
	<td></td>
	<td align="center">
	<div class="ticker">
		<marquee behavior="scroll" direction="left" scrollamount="1" width="500">This might be what the ticker looks like; hover over me to stop!</marquee>
	</div>
	</td>
</tr>
</table>

</body>
</html>