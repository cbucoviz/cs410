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

<div class="city_page_background">
	<table class="city_event_table">
		<tr>
			<td>	
				<ul id="tabmenu">
					<li><a class="active" href="">Event</a></li>
					<li><a href="">Filter</a></li>
				</ul>
				
				<div id="content">
				<p>blah</p>
				</div>
			</td>
			<td>
				<div class="city_map">
				</div>
			</td>
		</tr>
	</table>

</div>

</body>
</html>