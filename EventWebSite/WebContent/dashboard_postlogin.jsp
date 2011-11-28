<%@page import="Models.Security"%>
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



<table id="dashboard_frame">
	<tr>
		<td id="dashboard_main_cell">
		
			<a class="button1" style="float: right" href="home.jsp"><- Return to Main</a>
			<br>
			
			<div id="dashboard_main">
				<b><i><h1 id="username_header"></h1></i></b>
				<a id="view_profile_btn" class="button1" href="profilepage.jsp">View Profile</a><br/><br/>
				<a id="view_calendar_btn" class="button1" href="calendar.jsp">View Calendar</a><br/><br/>
				<% 
					Boolean loggedIn = (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN);
					if(loggedIn != null && loggedIn == true)
					{
						Integer userId = (Integer) session.getAttribute(Servlets.SessionVariables.USER_ID);
						boolean isAdmin = Models.Security.isAdmin(userId);
						if(isAdmin)
						{
				%>				
							<a id="manage_mod_btn" class="button1" href="modpage.jsp">Manage Moderators</a>
				<%
						}
					}
				%>
				<br>
				<br>
				<br>
				
				<div id="my_events_div">
					<div class="profile_header">
						<font size="3">My Events</font>
					</div>
					<div id="my_events_content">
						<ul id="my_events_list">
							<li class="event_item">
								<a href="EventPage?eventID=10">My Dummy Event</a>
							</li>
						</ul>
					</div>
				</div>
				
				<div id="subs_events_div">
					<div class="profile_header">
						<font size="3">Subscribed Events</font>
					</div>
					<div id="subs_events_content">
						<ul id="subs_events_list">
							<li class="event_item">
								<a href="EventPage?eventID=">Subscribed Dummy Event</a>
							</li>
						</ul>
					</div>
				</div>
				
				<div id="subs_locale_div">
					<div class="profile_header">
						<font size="3">Subscribed Locales</font>
					</div>
					<div id="subs_locale_content">
						<ul id="subs_locale_list">
							<li class="event_item">
								<a href="EventPage?eventID=">Subscribed Dummy Locale</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<form action='Login' method='POST'>
				<input type='hidden' name='isLogin' value='false'/>
				<button id="logout_btn" name="logout_btn" type="submit" value="log_out" class="button1">Log Out</button>
			</form>
		</td>
		<td style="width:40px;text-align:center;valign:middle">
			<font color="yellow"><big> >>> </big></font>
		</td>
	</tr>
</table>

</body>
</html>