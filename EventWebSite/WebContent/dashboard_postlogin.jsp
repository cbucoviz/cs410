<%@page import="Models.Search"%>
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


$(document).ready(function()
		{
			// any <div> with the popup attribute will have a qtip associated with it
			$('div[popup]').each(function() 
			{
				$(this).qtip(
				{
					 content: {
					      url: "popup.jsp?eventId=" + $(this).attr('popup')
					 },
					 position: {
					     corner: {
					          target: 'rightTop',
					          tooltip: 'topLeft'    
					     }
					 },
					 hide: {
					      when: 'mouseout',
					      fixed: true,
					      delay: 100,
					      effect: {
					    		type: 'fade',
					    		length: 700
					      }
					 },
					 style: {
						 name: 'green',
						 tip: 'leftTop'
					 }
				});
			});
		});
</script>

</head>
<body>

<%@ page import="java.util.*" %>
<% int ITEM_TEXT_SIZE = 25;%>

<table id="dashboard_frame">
	<tr>
		<td id="dashboard_main_cell">
		
			<a class="button1" style="float: right" href="home.jsp"><- Return to Main</a>
			
			<div id="dashboard_main">
				<b><i><h1 id="username_header"></h1></i></b>
				
				<a id="view_profile_btn" class="button1" href="profilepage.jsp">View Profile</a>
				<a id="view_calendar_btn" class="button1" href="calendar.jsp">View Calendar</a>
				<br/><br/>
				
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
				
				<div id="my_events_div">
				
					<div class="profile_header">
						<font size="3">My Events</font>
						<a id="create_event_btn" class="button1" href="editevent.jsp" style="float: right;">Create Event</a>
					</div>
					
					<div class="dashboard_scroll_list">
						<div id="my_event_list_container" class="scroll_list_container">
						
								<% 
									if(loggedIn != null && loggedIn == true)
									{
										Integer userId = (Integer) session.getAttribute(Servlets.SessionVariables.USER_ID);
										List<Map<Models.Search.EventInfoSearch,String>> myEvents = Models.Search.findMyEvents(userId);
										
										for(int i = 0; i < myEvents.size(); i++)
										{
											Map<Models.Search.EventInfoSearch,String> currEvent = myEvents.get(i);
											int eventId = Integer.parseInt(currEvent.get(Models.Search.EventInfoSearch.EVENT_ID));
											String title = currEvent.get(Models.Search.EventInfoSearch.TITLE);
											String isEdited = currEvent.get(Models.Search.EventInfoSearch.IS_EDITED);
											
											if(title.length() > ITEM_TEXT_SIZE)
											{
												title = title.substring(0, ITEM_TEXT_SIZE);
											}
											
											out.println("<div class='scroll_item' class='event_item' popup='" + eventId + "'>");
											
											if (isEdited.equals("Is Edited"))
											{
												out.println("<a href='EventPage?eventID=" + eventId + "'><font class='blink'>* </font>" + title + "</a>");
											}
											else
											{
												out.println("<a href='EventPage?eventID=" + eventId + "'>" + title + "</a>");
											}
											
											out.println("</div>");
										}
									}
								%>
								
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
					
					<div class="dashboard_scroll_list">
						<div id="subs_event_list_container" class="scroll_list_container">
							
							<% 
								if(loggedIn != null && loggedIn == true)
								{
									Integer userId = (Integer) session.getAttribute(Servlets.SessionVariables.USER_ID);
									List<Map<Models.Search.EventInfoSearch,String>> subsEvents = Models.Search.findMySubscribedEvents(userId);
									
									for(int i = 0; i < subsEvents.size(); i++)
									{
										Map<Models.Search.EventInfoSearch,String> currEvent = subsEvents.get(i);
										int eventId = Integer.parseInt(currEvent.get(Models.Search.EventInfoSearch.EVENT_ID));
										String title = currEvent.get(Models.Search.EventInfoSearch.TITLE);
										String isEdited = currEvent.get(Models.Search.EventInfoSearch.IS_EDITED);
										
										if(title.length() > ITEM_TEXT_SIZE)
										{
											title = title.substring(0, ITEM_TEXT_SIZE);
										}
										
										out.println("<div class='scroll_item' class='event_item' popup='" + eventId + "'>");
										
										if (isEdited != null && isEdited.equals("Is Edited"))
										{
											out.println("<a href='EventPage?eventID=" + eventId + "'><font class='blink'>* </font>" + title + "</a>");
										}
										else
										{
											out.println("<a href='EventPage?eventID=" + eventId + "'>" + title + "</a>");
										}
										
										
										
										out.println("</div>");
									}
								}
							%>
							
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
				
				<div id="subs_users_div">
					<div class="profile_header">
						<font size="3">Subscribed Users</font>
					</div>
					
					<div class="dashboard_scroll_list">
						<div id="subs_user_list_container" class="scroll_list_container">
							
							<% 
								if(loggedIn != null && loggedIn == true)
								{
									Integer userId = (Integer) session.getAttribute(Servlets.SessionVariables.USER_ID);
									List<Map<Models.Search.UserInfoSearch,String>> subsUsers = Models.Search.findSubscribedUsers(userId);
									
									for(int i = 0; i < subsUsers.size(); i++)
									{
										Map<Models.Search.UserInfoSearch,String> user = subsUsers.get(i);
										String name = user.get(Models.Search.UserInfoSearch.NAME);
										int numNewEvents = 0;
										int creatorId = Integer.parseInt(user.get(Models.Search.UserInfoSearch.USER_ID));
										String temp = user.get(Models.Search.UserInfoSearch.NUM_NEW_EVENTS);
										numNewEvents = Integer.parseInt(temp);
										/*if (temp != null)
										{
											isEdited = Integer.parseInt(temp);
										}*/
										
										if(name.length() > ITEM_TEXT_SIZE)
										{
											name = name.substring(0, ITEM_TEXT_SIZE);
										}
										
										out.println("<div class='scroll_item'>");
										
										if (numNewEvents > 0)
										{
											out.println("<a href='#'><font class='blink'>* </font><font color='yellow'>(" + numNewEvents + ") </font>" + name + "<button type='button' class='button1' style='float: right;' subscribe='user' subscribeId='" + creatorId + "'>Unsubscribe</button></a>");
										
										}
										else
										{
											out.println("<a href='#'>" + name + "<button type='button' class='button1' style='float: right;' subscribe='user' subscribeId='" + creatorId + "'>Unsubscribe</button></a>");
										}
										
										
										
										out.println("</div>");
									}
								}
							%>
							
						</div>
					</div>
					
					<table class="scroll_list_btns">
						<tr>
						<td>
							<p align="right">
								<a href="#" onMouseover="move('subs_user_list_container',2)" onMouseout="clearTimeout(move.to)"><img src="resources/up.gif" border=0></a>  
								<a href="#" onMouseover="move('subs_user_list_container',-2)" onMouseout="clearTimeout(move.to)"><img src="resources/down.gif" border=0></a>
							</p>
						</td>
						</tr>
					</table>
					
				</div>
				
				<div id="subs_locale_div">
					<div class="profile_header">
						<font size="3">Subscribed Locales</font>
					</div>
					
					<div class="dashboard_scroll_list">
						<div id="subs_locale_list_container" class="scroll_list_container">
						
							<% 
								if(loggedIn != null && loggedIn == true)
								{
									Integer userId = (Integer) session.getAttribute(Servlets.SessionVariables.USER_ID);
									List<Map<Models.Search.SubscribedLocaleInfo,String>> locales = Models.Search.findSubscribedLocales(userId);
									
									for(int i = 0; i < locales.size(); i++)
									{
										Map<Models.Search.SubscribedLocaleInfo,String> currEvent = locales.get(i);
										int locId = Integer.parseInt(currEvent.get(Models.Search.SubscribedLocaleInfo.LOCALE_ID));
										String city = currEvent.get(Models.Search.SubscribedLocaleInfo.CITY);
										int numNewEvents = Integer.parseInt(currEvent.get(Models.Search.SubscribedLocaleInfo.NUM_NEW_EVENTS));
										
										if(city.length() > ITEM_TEXT_SIZE)
										{
											city = city.substring(0, ITEM_TEXT_SIZE);
										}
										
										out.println("<div class='scroll_item'>");
										
										if (numNewEvents > 0)
										{
											out.println("<a href='citypage.jsp?city=" + locId + "'><font class='blink'>*</font><font color='yellow'>(" + numNewEvents +")</font> "+ city + "<button type='button' class='button1' style='float: right;' subscribe='locale' subscribeId='" + locId + "'>Unsubscribe</button></a>");
										}
										else
										{
											out.println("<a href='citypage.jsp?city=" + locId + "'>" + city + "<button type='button' class='button1' style='float: right;' subscribe='locale' subscribeId='" + locId + "'>Unsubscribe</button></a>");
										}
										
										out.println("</div>");
									}
								}
							%>
							
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
