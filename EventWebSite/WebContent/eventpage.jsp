<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- BEGINNING OF INCLUDES  -->
<%@ include file="config/constants.jsp" %>
<%@ page import="java.util.ArrayList" %>
<link href="config/default.css" rel="stylesheet" type="text/css"/>
<link href="config/revComPostStyle.css" rel="stylesheet" type="text/css"/>
<link href="config/statistics.css" rel="stylesheet" type="text/css"/>
<!-- END OF INCLUDES -->

<script src="scripts/forwarder.js" type="text/javascript"></script>
<script src="scripts/animatedcollapse.js" type="text/javascript"></script>
<script src="scripts/eventPageTags.js" type="text/javascript"></script>
<script src="scripts/eventStats.js" type="text/javascript"></script>
<script src="scripts/showHideCom.js" type="text/javascript"></script>
<script src="scripts/eventMap.js" type="text/javascript"></script>

<script type="text/javascript">

		// hook in the abuse dialog
		$("button[name=report_post_button]").click(function()
			{
				$('#report_abuse_form').dialog('open');
			}
		);
		
		$("button[name=report_event_button]").click(function()
				{
					$('#report_abuse_form').dialog('open');
				}
		);
</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= WEBSITE_TITLE %></title>

</head>

<body>
	<div class="event_container">
		<div class="event_name_header">
		
			<a  class="button1"  href="citypage.jsp?city=<%= request.getAttribute("locationID") %>" style="margin-left: 10px;">Return to <%= request.getAttribute("city") %></a>
			<button type="button" name="report_event_button"class="button1"  value="report_event" style="float: right; margin-right: 20px;">Report This Event</button>
			<br>
			<br>
			
			<table class="event_name_header">
				<tr>
					<td colspan="2">
						<i><b><font size="6"><%= request.getAttribute("title") %></font></b></i>
						
						<% if(session.getAttribute(Servlets.SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN)){ %>					
						<button type="button" name="subs_event_button" class="button1" value="subscribe_event" subscribe="event" subscribeId="<%= request.getAttribute("eventID") %>">
						<%
							if(Models.Event.isSubscribed((Integer) session.getAttribute(Servlets.SessionVariables.USER_ID), (Integer) request.getAttribute("eventID")))
							{
								out.println("Unsubscribe from the Event");
							}
							else
							{
								out.println("Subscribe to the Event");
							}
						%>
						</button>
						<% } %>
<br>
						<i><b>Category: <font class="sports">Sports</font></b></i>
						<br>
						<br>
					</td>
				</tr>
				<tr>
					<td>
						<font size="2"><b>LOCATION: </b><%= request.getAttribute("city") %>, <%= request.getAttribute("state") %>, <%= request.getAttribute("country") %> </font>
						
						
						<% if(session.getAttribute(Servlets.SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN)){ %>

							<button type="button" name="subs_locale_button" class="button1" subscribe="locale" subscribeId='<%= request.getAttribute("locationID") %>' value="subscribe_locale">
							<% 
							if(Models.Location.isSubscribed((Integer) session.getAttribute(Servlets.SessionVariables.USER_ID), (Integer) request.getAttribute("locationID")))
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
					</td>
					<td><font size="2"><b>POSTED BY: </b><%= request.getAttribute("creator") %></font>
					<% if(session.getAttribute(Servlets.SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN)){ %>					
						<button type="button" name="subs_user_button" class="button1" value="subscribe_user" subscribe="user" subscribeId="<%= request.getAttribute("creatorID") %>">
						<%
							if(Models.User.isSubscribed((Integer) session.getAttribute(Servlets.SessionVariables.USER_ID), (Integer) request.getAttribute("creatorID")))
							{
								out.println("Unsubscribe from User");
							}
							else
							{
								out.println("Subscribe to User");
							}
						%>
						</button>
					<% } %>
					</td>
				</tr>
			</table>
		</div>
		<div class="event_content">
			<table class="event_content">
				<tr>
					<td style="width: 760px">
						<!-- Rating, Time, Address of Event -->
						
						<!-- Only show "Edit Event" button when the logged-in user is the owner of this event -->
						<% if(session.getAttribute(Servlets.SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN)){ %>					
							<a href="editevent.jsp?eventId=<%= request.getAttribute("eventID") %>" class="button1" style="float: right">Edit Event</a> 
						<% } %>
						
						<!-- Only display "Attend Button" before event occur -->
						<b><div style="display: inline" id="attendeesLabel" numAttendees="<%= request.getAttribute("numAttendees") %>"><%= request.getAttribute("numAttendees") %> people are attending this event!</div></b>
						
						<% if(session.getAttribute(Servlets.SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN)){ %>					
						<button type="button" name="attend_button" class="button1" value="attend_event" attend="<%= request.getAttribute("eventID") %>">
						<%
							if(Models.Event.isAttending((Integer) request.getAttribute("eventID"), (Integer) session.getAttribute(Servlets.SessionVariables.USER_ID)))
							{
								out.println("Unattend this Event");
							}
							else
							{
								out.println("Attend this Event");
							}
						%>
						</button>
						<% } %>
						<br>
						<br>
						
						<table>
						
							<!-- Only display "Rating" if event had occurred -->
							<tr>
								<td style="text-align: right">
									<b>Rating: </b>
								</td>
								<td>
									<!-- ***Insert rating image here!!! --> <i><%= request.getAttribute("rating") %></i>
								</td>
							</tr>
							
							<!-- ***Venue might not be available... -->
							<tr>
								<td style="text-align: right">
									<b>Venue: </b>
								</td>
								<td>
									<i><%= request.getAttribute("venue") %></i>
								</td>
							</tr>
							
							<tr>
								<td style="text-align: right">
									<b>Address:</b>
								</td>
								<td>
									<i><%= request.getAttribute("address") %></i>
								</td>
							</tr>
							
							<tr>
								<td style="text-align: right">
									<b>Date: </b>
								</td>
								<td>
									<i><% 
									java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MMM dd, yyyy");
									java.util.Date date = (java.util.Date) request.getAttribute("eventDate");
									out.println(formatter.format(date));
									%></i>
								</td>
							</tr>
							
							<tr>
								<td style="text-align: right">
									<b>Time: </b>
								</td>
								<td>
									<i>
									<i><% 
									java.text.SimpleDateFormat timeFormatter = new java.text.SimpleDateFormat("HH:mm:ss");
									java.util.Date startTime = (java.util.Date) request.getAttribute("startTime");
									java.util.Date endTime = (java.util.Date) request.getAttribute("endTime");
									out.println(timeFormatter.format(startTime) + " - " + timeFormatter.format(endTime));
									%></i>
									</i>
								</td>
							</tr>
						
						</table>
						
					</td>
					<td rowspan="2" style="width: 300px; vertical-align: top">
						<!-- Top X Events Recommendation -->
						<div class="top_event_recommendations" >
							<h4 style="text-align: center">Top 10 Local Suggestions:</h4>
							
							<!--  One event_recommendation div per recommendation -->
							<% ArrayList<ArrayList<String>> topEvents = (ArrayList<ArrayList<String>>) request.getAttribute("topEvents");%>
							<%for(int i=0; i<topEvents.size(); i++) {%>
								<div class="event_recommendation">
								 
									<font color="blue"><b> Event <%=i+1%></b></font>
									<br/>
									
									<a href="EventPage?eventID=<%=topEvents.get(i).get(8) %>"><%=topEvents.get(i).get(0) %></a>
									<br/>
									
									<b>Venue: </b><%=topEvents.get(i).get(1) %>
									<br/>
									
									<b>Date: </b> <%=topEvents.get(i).get(2) %>
									<br/>
									
									<b>Time: </b> <%=topEvents.get(i).get(3) %> - <%=topEvents.get(i).get(4) %>
									<br/>
									
									<b>Creator: </b> <%=topEvents.get(i).get(5) %>
									
									<% if(session.getAttribute(Servlets.SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN)){ %>					
									<button type="button" name="subs_event_button" class="button1" value="subscribe_event" subscribe="user" subscribeId="<%=topEvents.get(i).get(6)%>">
									<%
										if(Models.User.isSubscribed((Integer) session.getAttribute(Servlets.SessionVariables.USER_ID), Integer.parseInt(topEvents.get(i).get(6))))
										{
											out.println("Unsubscribe from User");
										}
										else
										{
											out.println("Subscribe to User");
										}
									%>
									</button>
									<% } %>
									
									<br/>
																		
									<a href="EventPage?eventID=<%=topEvents.get(i).get(8) %>" style="float: right">More...</a>
									<br/>
								</div>
								<br/>
							<%} %>
							
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<!-- Tab pane for info, review, map, statistics -->
						<div id="ddimagetabs" class="halfmoon" style="width: 700px">
							<ul>
								<li class="selected"><a onmousedown="expandcontent('event_info_tab', this)">Event Information</a></li>
								<li><a onmousedown="revealPosts(<%=request.getAttribute("eventID")%>, 'rating'); expandcontent('event_discs_tab', this)">Discussions</a></li> <!-- user 'style="display:none"' to hide tab -->
								<li><a onmousedown="revealReviews(<%=request.getAttribute("eventID")%>, 'event_rating'); expandcontent('event_reviews_tab', this)">Reviews</a></li>
								<li><a ondblclick="initializeEMap(); expandcontent('event_map_tab', this)">Map</a></li>
								<li><a onmousedown="revealStats(<%=request.getAttribute("eventID")%>); expandcontent('event_stat_tab', this)">Statistics</a></li>
							</ul>
						</div>
						
						<!--  Individual tab pages -->
						<div id="event_tab_container">
						
							<div id="event_info_tab" class="event_tab_page">
								<div id="event_desc_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Event Description:</h4>
									</div>
									<div class="event_info_content">
										<p>
											<%= request.getAttribute("genDesc") %>
										</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_venue_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Venue Description:</h4>
									</div>
									<div class="event_info_content">
										<p>
											<%= request.getAttribute("venueDesc") %>
										</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_cost_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Cost (& Methods of Payment, if applicable):</h4>
									</div>
									<div class="event_info_content">
										<p>
											<%= request.getAttribute("priceDesc") %>
										</p>																			
									</div>
								</div>
								<br>
								<br>
								<div id="event_transport_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Commute & Driving Directions:</h4>
									</div>
									<div class="event_info_content">
										<p>
											<%= request.getAttribute("transportDesc") %>
										</p>	
									</div>
								</div>
								<br>
								<br>
								<div id="event_others_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Other Useful Information:</h4>
									</div>
									<div class="event_info_content">
										<p>
											<%= request.getAttribute("otherInfo") %>
										</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_vid_link_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Related Videos:</h4>
									</div>
									<div class="event_info_content">
										<p>
											<%= request.getAttribute("videos") %>
										</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_links_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Related Links:</h4>
									</div>
									<div class="event_info_content">
										<p>
											<%= request.getAttribute("links") %>
										</p>
									</div>
								</div>
								
							</div>
							
							<div id="event_discs_tab" class="event_tab_page">
							
							
							
								<!-- "One" event_discs_post div for each discussion post.-->
								
								
									
										
							</div>
							
							
							<div id="event_reviews_tab" class="event_tab_page">
								
								<!-- One event_review_post div per 1 review -->
																
								<!-- Area for writing and posting reviews. -->
								
								
							</div>
							
							<div id="event_map_tab" class="event_tab_page">
								<div  locid='<%=request.getAttribute("locationID") %>' title='<%=request.getAttribute("title") %>'id="event_map">
									
								</div>
								<div class="event_map_dir">
									<h4><b>Directions to Event Location:</b></h4>
									<p class="event_map_dir">
										<%= request.getAttribute("transportDesc") %>
									</p>
								</div>
							</div>
							
							<div id="event_stat_tab" class="event_tab_page">
								
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>






<!--  include the report_abuse_dialog, we need to do it down here, this page loads too slow 
and jquery can't hide it fast enough -->
<%@ include file="report_abuse_dialog.jsp" %>


</body>
</html>
