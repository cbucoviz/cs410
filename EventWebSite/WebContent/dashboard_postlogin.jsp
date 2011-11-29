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
	

<script type="text/javascript">

function move(id,spd){
 var obj=document.getElementById(id),max=-obj.offsetHeight+obj.parentNode.offsetHeight,top=parseInt(obj.style.top);
 if ((spd>0&&top<=0)||(spd<0&&top>=max)){
  obj.style.top=top+spd+"px";
  move.to=setTimeout(function(){ move(id,spd); },20);
 }
 else {
  obj.style.top=(spd>0?0:max)+"px";
 }
}

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
				<a id="view_profile_btn" class="button1" href="profilepage.jsp">View Profile</a>
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
					
					<div class="event_scroll_list">
						<div id="my_event_list_container" class="scroll_list_container">
						
								<!-- INSERT REAL MY EVENTS HERE -->
								
								<div class="scroll_item">
									<a href="EventPage?eventID=10">My Dummy Event 1</a>
								</div>
								<div class="scroll_item">
									<a href="EventPage?eventID=10">My Dummy Event 2</a>
								</div>
								<div class="scroll_item">
									<a href="EventPage?eventID=10">My Dummy Event 3</a>
								</div>
								<div class="scroll_item">
									<a href="EventPage?eventID=10">My Dummy Event 4</a>
								</div>
								<div class="scroll_item">
									<a href="EventPage?eventID=10">My Dummy Event 5</a>
								</div>
								<div class="scroll_item">
									<a href="EventPage?eventID=10">My Dummy Event 6</a>
								</div>
								<div class="scroll_item">
									<a href="EventPage?eventID=10">My Dummy Event 7</a>
								</div>
						</div>
					</div>
					
					<table class="scroll_list_btns">
						<tr>
						<td>
							<p align="right">
								<a href="#" onMouseover="move('my_event_list_container',2)" onMouseout="clearTimeout(move.to)"><img src="resources/up.gif" border=0></a>  
								<a href="#" onMouseover="move('my_event_list_container',-2)" onMouseout="clearTimeout(move.to)"><img src="resources/down.gif" border=0></a>
							</p>
						</td>
						</tr>
					</table>
				</div>
				
				<div id="subs_events_div">
					<div class="profile_header">
						<font size="3">Subscribed Events</font>
					</div>
					
					<div class="event_scroll_list">
						<div id="subs_event_list_container" class="scroll_list_container">
							
							<!-- INSERT REAL SUBSCRIBED EVENTS HERE -->
							
							<div class="scroll_item">
								<a href="EventPage?eventID=">Subscribed Dummy Event 1</a>
							</div>
							<div class="scroll_item">
								<a href="EventPage?eventID=">Subscribed Dummy Event 2</a>
							</div>
							<div class="scroll_item">
								<a href="EventPage?eventID=">Subscribed Dummy Event 3</a>
							</div>
							<div class="scroll_item">
								<a href="EventPage?eventID=">Subscribed Dummy Event 4</a>
							</div>
						</div>
					</div>
					
					<table class="scroll_list_btns">
						<tr>
						<td>
							<p align="right">
								<a href="#" onMouseover="move('subs_event_list_container',2)" onMouseout="clearTimeout(move.to)"><img src="resources/up.gif" border=0></a>  
								<a href="#" onMouseover="move('subs_event_list_container',-2)" onMouseout="clearTimeout(move.to)"><img src="resources/down.gif" border=0></a>
							</p>
						</td>
						</tr>
					</table>
					
				</div>
				
				<div id="subs_locale_div">
					<div class="profile_header">
						<font size="3">Subscribed Locales</font>
					</div>
					
					<div class="event_scroll_list">
						<div id="subs_locale_list_container" class="scroll_list_container">
						
							<!-- INSERT REAL SUBSCRIBED LOCALES HERE -->
							
							<div class="scroll_item">
								<a href="EventPage?eventID=">Subscribed Dummy Locale 1</a>
							</div>
							<div class="scroll_item">
								<a href="EventPage?eventID=">Subscribed Dummy Locale 2</a>
							</div>
							<div class="scroll_item">
								<a href="EventPage?eventID=">Subscribed Dummy Locale 3</a>
							</div>
							<div class="scroll_item">
								<a href="EventPage?eventID=">Subscribed Dummy Locale 4</a>
							</div>
						</div>
					</div>
					
					<table class="scroll_list_btns">
						<tr>
						<td>
							<p align="right">
								<a href="#" onMouseover="move('subs_locale_list_container',2)" onMouseout="clearTimeout(move.to)"><img src="resources/up.gif" border=0></a>  
								<a href="#" onMouseover="move('subs_locale_list_container',-2)" onMouseout="clearTimeout(move.to)"><img src="resources/down.gif" border=0></a>
							</p>
						</td>
						</tr>
					</table>
					
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