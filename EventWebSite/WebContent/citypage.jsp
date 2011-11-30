<%@page import="Models.Location"%>
<%@page import="Models.LocationAddress"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- BEGINNING OF INCLUDES  -->
<%@ include file="config/constants.jsp" %>
<link href="config/default.css" rel="stylesheet" type="text/css" />
<script src="scripts/ezcalendar_orange.js" type="text/javascript"></script>
<script src="scripts/forwarder.js" type="text/javascript"></script>
<script src="scripts/citymap.js" type="text/javascript"></script>
<!-- END OF INCLUDES -->

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= WEBSITE_TITLE %></title>


<script type="text/javascript">

/***********************************************
* DD Tab Menu II script- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

//Set tab to intially be selected when page loads:
//[which tab (1=first tab), ID of tab content to display (or "" if no corresponding tab content)]:
var initialtab=[1, "city_event_tab"]

//Turn menu into single level image tabs (completely hides 2nd level)?
var turntosingle=0 //0 for no (default), 1 for yes

//Disable hyperlinks in 1st level tab images?
var disabletablinks=0 //0 for no (default), 1 for yes


////////Stop editting////////////////

var previoustab="city_event_tab"

if (turntosingle==1)
{
	document.write('<style type="text/css">\n#tab_content_container{display: none;}\n</style>')
}

function expandcontent(cid, aobject){
	if (disabletablinks==1)
	{
		aobject.onclick=new Function("return false")
	}
	
	if (document.getElementById && turntosingle==0){
		highlighttab(aobject)
		if (previoustab!="")
		{
			document.getElementById(previoustab).style.display="none"
		}
		
		if (cid!=""){
			document.getElementById(cid).style.display="block"
			previoustab=cid
		}
	}
}

function highlighttab(aobject){
	if (typeof tabobjlinks=="undefined")
	{
		collectddimagetabs()
	}
	for (i=0; i<tabobjlinks.length; i++)
	{
		tabobjlinks[i].className=""
	}
	aobject.className="current"
}

function collectddimagetabs(){
	var tabobj=document.getElementById("ddimagetabs")
	tabobjlinks=tabobj.getElementsByTagName("A")
}

function do_onload(){
	collectddimagetabs()
	expandcontent(initialtab[1], tabobjlinks[initialtab[0]-1])
}

if (window.addEventListener)
{
	window.addEventListener("load", do_onload, false)
}
else if (window.attachEvent)
{
	window.attachEvent("onload", do_onload)
}
else if (document.getElementById)
{
	window.onload=do_onload
}



$(document).ready(function()
{
	// any <li> with the popup attribute will have a qtip associated with it
	$('li[popup]').each(function() 
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

<div class="city_page_background">
	<table class="city_event_table">
		<tr>
			<td id="city_tab_td">	
			
				<div class="city_name_tag">
					<table>
						<tr>
							<td>
								<!-- Insert Local Name Here (Replace next line)-->
								<h4>Locale:
								<% 
									LocationAddress location = Models.Location.getLocationAddress(Integer.parseInt(request.getParameter("city")));
									out.print(location.toString());
								%>
								</h4>
							</td>
						</tr>
					</table>
				</div>
					
				<div id="ddimagetabs" class="halfmoon">
					<ul>
						<li class="selected"><a onmousedown="expandcontent('city_event_tab', this)">Event</a></li>
						<li><a onmousedown="expandcontent('city_filter_tab', this)">Filter</a></li>
						<li><a onmousedown="expandcontent('city_top_events_tab', this)">Top Events</a></li>
						<li><a onmousedown="expandcontent('city_top_users_tab', this)">Top Users</a></li>
					</ul>
				</div>
				
				<div id="tab_content_container">
				
					<div id="city_event_tab" class="city_tab_page">
						<div id="cul_event_div" class="city_event_categories">
							<div id="cul_header_div" class="city_event_header">
								<h4>Cultural</h4>
							</div>
							
							<div class="event_list">
								<%@ page import="java.util.*" %>
								<ul>
								<%ArrayList<Map<Models.Event.EventInfo, String>> events = Models.Location.getEventsAtLocation(Integer.parseInt(request.getParameter("city")));
									for(int i = 0; i < events.size(); ++i)
									{
										Map<Models.Event.EventInfo, String> event = events.get(i);
										int eventId = Integer.parseInt(event.get(Models.Event.EventInfo.EVENT_ID));
										String title = event.get(Models.Event.EventInfo.TITLE);
										
										out.println("<li class='event_item' popup='" + eventId + "'><a href='EventPage?eventID=" + eventId + "'>" + title + "</a></li>");
									}
								%>
								</ul>
							</div>
							
						</div>
						<div id="ed_event_div" class="city_event_categories">
							<div id="ed_header_div" class="city_event_header">
								<h4>Education</h4>
							</div>
							
							<div class="event_list">
							</div>
						</div>
						<div id="music_event_div" class="city_event_categories">
							<div id="music_header_div" class="city_event_header">
								<h4>Music</h4>
							</div>
							
							<div class="event_list">
							</div>
						</div>
						<div id="sports_event_div" class="city_event_categories">
							<div id="sports_header_div" class="city_event_header">
								<h4>Sports</h4>
							</div>
							
							<div class="event_list">
							</div>
						</div>
						<div id="others_event_div" class="city_event_categories">
							<div id="others_header_div" class="city_event_header">
								<h4>Others</h4>
							</div>
							
							<div class="event_list">
							</div>
						</div>
					</div>
					
					<div id="city_filter_tab" class="city_tab_page">
						<form action="Search" method="POST">
							<h3>Search For Events</h3>
							<h5>Enter a Keyword or Phrase:</h5>
							<input id="filter_search_input" type="text" name="keywords" size="55"/>
							<br>
							<br>
							<hr style="margin-left:30px">
							<h4>Advanced</h4>
							<table>
								<tr>
									<td>
										<font size="2"><b>Date:</b></font>
										<input name="search_date" id="search_date" type="text" size="10" maxlength="10" value="" style="margin-top:-10px"/>(dd/mm/yyyy)

									</td>
								</tr>
								<tr>
									<td>&nbsp</td>
								</tr>
								<tr>
									<td>
										<font size="2"><b>Categories:</b></font>
									</td>
								</tr>
								<tr>
									<td>
									<!-- Add more categories here in this cell if needed -->
										<input type="checkbox" name="event_categories" value="cultural"/><font size="1">Cultural</font>
										<br>
										<input type="checkbox" name="event_categories" value="education"/><font size="1">Education</font>
										<br>
										<input type="checkbox" name="event_categories" value="music"/><font size="1">Music</font>
										<br>
										<input type="checkbox" name="event_categories" value="sports"/><font size="1">Sports</font>
										<br>
										<input type="checkbox" name="event_categories" value="others"/><font size="1">Others</font>
										<br>
										<br>
									</td>
								</tr>
								<tr>
									<td>
										<input type="submit" class="button1" value="Search" />
									</td>
								</tr>
							</table>
						</form>
					</div>
					
					<div id="city_top_events_tab" class="city_tab_page">
						<ol>
						<% 	List<Map<Models.Search.EventInfoSearch,String>> topEvents = Models.Search.topEvents(Integer.parseInt(request.getParameter("city")));
							
							for(int i = 0; i < topEvents.size(); i++)
							{
								Map<Models.Search.EventInfoSearch,String> event = topEvents.get(i);
								int eventId = Integer.parseInt(event.get(Models.Search.EventInfoSearch.EVENT_ID));
								String title = event.get(Models.Search.EventInfoSearch.TITLE);
								
								out.println("<li class='event_item' popup='" + eventId + "'><a href='EventPage?eventID=" + eventId + "'>" + title + "</a></li>");
							}
						
						%>
						</ol>
					</div>
					
					<div id="city_top_users_tab" class="city_tab_page">
						<ol>
						<% 	List<Map<Models.Search.UserInfoSearch,String>> topUsers = Models.Search.topUsers(Integer.parseInt(request.getParameter("city")));
							
							for(int i = 0; i < topUsers.size(); i++)
							{
								Map<Models.Search.UserInfoSearch,String> user = topUsers.get(i);
								int userId = Integer.parseInt(user.get(Models.Search.UserInfoSearch.USER_ID));
								String name = user.get(Models.Search.UserInfoSearch.NAME);
								
								//out.println("<li class='event_item' popup='" + eventId + "'><a href='EventPage?eventID=" + eventId + "'>" + title + "</a></li>");
								out.println("<li class='event_item'><a href='#'>" + name + "</a></li>");
							}
						
						%>
						</ol>
					</div>
					
				</div>
			</td>
			<td id="city_map_td">
				<div class="city_map_container">
				
					<div id="city_buttons">
						<% if(session.getAttribute(Servlets.SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN)){ %>
							<a class="button1" href="editevent.jsp?locationId=<%= request.getParameter("city") %>" class="button1">Create Event at Locale</a>
						
							<button type="button" name="subs_locale_button" class="button1" subscribe="locale" subscribeId='<%= request.getParameter("city") %>' value="subscribe_locale">
							<% 
							if(Models.Location.isSubscribed((Integer) session.getAttribute(Servlets.SessionVariables.USER_ID), Integer.parseInt(request.getParameter("city"))))
							{
								out.println("Unsubscribe from Locale");
							}
							else
							{
								out.println("Subscribe to Locale");
							}
							%>
							</button>
						<% } %> 
										
					</div>
					
					<div locid='<%=Integer.parseInt(request.getParameter("city")) %>' id="city_map">
						
					</div>
				</div>
				
			</td>
		</tr>
	</table>

</div>
<script type="text/javascript">
	window.onload = initialize();
	window.onunload = GUnload();
</script>
</body>
</html>
