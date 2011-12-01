<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- BEGINNING OF INCLUDES  -->
<link href="config/default.css" rel="stylesheet" type="text/css" />
<script src="scripts/ezcalendar_orange.js" type="text/javascript"></script>
<script src="scripts/forwarder.js" type="text/javascript"></script>
<!-- END OF INCLUDES -->

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>


<div class="page_container">

	<!--  change the css style {display} of create_event_div and edit_event_div to toggle the two views -->
	
	<!-- Use this for CREATING NEW EVENT; set css style {display:none} to hide it and {display:block} to display it -->
	
	
		<div class="page_header">
			<h2><b>
			<%
				if (request.getParameter("eventId") == null) 
				{
					out.println("Create Event");
				}
				else
				{
					out.println("Edit Event");
				}
			%>
								
			</b></h2>
		</div>
		
		<div class="page_content" align="center">
		
		<%@ page import="java.util.*" %>
		
		<%
			Map<Models.Event.EventInfo, String> event = null;
			if(request.getParameter("eventId") != null) 
			{
				event = Models.Event.getExistingEvent(Integer.parseInt(request.getParameter("eventId")));
			}
			
			// first things first, let's determine if we're creating an event at a particular locale
			Models.LocationAddress address = new Models.LocationAddress("","","");			
			Object locationObj = request.getParameter("locationId");
			if(locationObj != null)
			{
				Integer locationId = Integer.parseInt((String) locationObj);
				address = Models.Location.getLocationAddress(locationId);
			}
				
		%>
		
		<div id="ed_event_content_div" align="center">
			<form action="EditEvent" method="POST">
				
				<% if (request.getParameter("eventId") != null) {%>
					<input type='hidden' name='eventId' value='<%=request.getParameter("eventId")%>'/>
				<% } %>
				
				<table>
					<tr>
						<td colspan="2" align="center">
							<font color="red"><i>(*) = Mandatory/Required Information</i></font> 
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right">
							
							<font color="red">*</font> Event Title: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<input type="text" name="title" size="25">
							<% } else {
								out.println("<input type='text' name='title' size='25' value='" + event.get(Models.Event.EventInfo.TITLE) + "'>");
							} %>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> Category: 
						</td>
						<td>
							<select name="event_category">
								<option value="Cultural">Cultural</option>
								<option value="Education">Education</option>
								<option value="Music">Music</option>
								<option value="Sports">Sports</option>
								<option value="Others">Others</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> City: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<input type="text" name="city" size="25" value="<%= address.city %>">
							<% } else {
								out.println("<input type='text' name='city' size='25' value='" + event.get(Models.Event.EventInfo.CITY) + "'");
							   } %>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> State: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<input type="text" name="state" size="25" value="<%= address.state %>">
							<% } else {
								out.println("<input type='text' name='state' size='25' value='" + event.get(Models.Event.EventInfo.STATE) + "'>");
							} %>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> Country: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<input type="text" name="country" size="25" value="<%= address.country %>">
							<% } else {
								out.println("<input type='text' name='country' size='25' value='" + event.get(Models.Event.EventInfo.COUNTRY) + "'");
							   } %>
						</td>
					</tr>
					<tr>
						<td align="right">
							Venue: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<input type="text" name="venue" size="25">
							<% } else {
								out.println("<input type='text' name='venue' size='25' value='" + event.get(Models.Event.EventInfo.VENUE) + "'");
							   } %>
							<i>(e.g. Rogers Arena)</i>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> Address: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<input type="text" name="address" size="25">
							<% } else {
								out.println("<input type='text' name='address' size='25' value='" + event.get(Models.Event.EventInfo.ADDRESS) + "'");
							   } %>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> Date: 
						</td>
						<td>
						<!-- 
							<input name="date" id="date" type="text" size="10" maxlength="10" value="" style="margin-top:-10px"/>
							<img src="resources/calendar.jpg" onclick="javascript: showCalendar('date')">
						-->
							<% if (request.getParameter("eventId") == null) {%>
								<input type="text" name="date" size="25"> 
							<% } else {
								java.text.SimpleDateFormat importer = new java.text.SimpleDateFormat("yyyy-dd-MM");
								Date eventDate = importer.parse(event.get(Models.Event.EventInfo.EVENT_DATE));
								java.text.SimpleDateFormat exporter = new java.text.SimpleDateFormat("dd/MM/yyyy");
								out.println("<input type='text' name='date' size='25' value='" + exporter.format(eventDate) + "'>");
							   } %>	
							dd/mm/yyyy
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> Start Time: 
						</td>
						<td>
							<select name="start_hour">
								<%
								Date startDate = null;
							 	if (request.getParameter("eventId") != null) 
								{
							 		String startDateString = event.get(Models.Event.EventInfo.START_TIME);
							 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("hh:mm:ss");
							 		startDate = formatter.parse(startDateString);
								}
							
								for(int i = 1; i <= 12; ++i)
								{
									String selected = "";
									if(startDate != null)
									{
										if(startDate.getHours() % 12 == i)
										{
											selected = "selected='yes'";
										}
									}
									out.println("<option " + selected + " value='" + i + "'>" + i + "</option>");
								}
							%>
							</select>
							:
							<select name="start_minute">
								<%
								for(int i = 0; i <= 55; i += 5)
								{
									String timeString = "" + i;
									if(i == 0)
									{
										timeString = "00";
									}
									else if(i == 5)
									{
										timeString = "05";
									}
									
									String selected = "";
									if(startDate != null)
									{
										if(startDate.getMinutes() == i)
										{
											selected = "selected='yes'";
										}
									}
									
									out.println("<option value='" + timeString + "'>" + timeString + "</option>");
								}
							%>
							</select>
							<select name="start_am_pm">							<%
									String amSelected = "";
									String pmSelected = "";
									
									if(startDate != null)
									{
										if(startDate.getHours() >= 12)
										{
											pmSelected = "selected='yes'";
										}
										else
										{
											amSelected = "selected='yes'";
										}
									}

									out.println("<option value='am' " + amSelected + ">AM</option>");
									out.println("<option value='pm' " + pmSelected + ">PM</option>");
							%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> End Time: 
						</td>
						<td>
							<select name="end_hour">
							<%
								Date endDate = null;
							 	if (request.getParameter("eventId") != null) 
								{
							 		String endDateString = event.get(Models.Event.EventInfo.END_TIME);
							 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("hh:mm:ss");
							 		endDate = formatter.parse(endDateString);
								}
							
								for(int i = 1; i <= 12; ++i)
								{
									String selected = "";
									if(endDate != null)
									{
										if(endDate.getHours() % 12 == i)
										{
											selected = "selected='yes'";
										}
									}
									out.println("<option " + selected + " value='" + i + "'>" + i + "</option>");
								}
							%>
							</select>
							:
							<select name="end_minute">
								<%
								for(int i = 0; i <= 55; i += 5)
								{
									String timeString = "" + i;
									if(i == 0)
									{
										timeString = "00";
									}
									else if(i == 5)
									{
										timeString = "05";
									}
									
									String selected = "";
									if(endDate != null)
									{
										if(endDate.getMinutes() == i)
										{
											selected = "selected='yes'";
										}
									}
									
									out.println("<option value='" + timeString + "'>" + timeString + "</option>");
								}
							%>
							</select>
							<select name="end_am_pm">
							<%
									amSelected = "";
									pmSelected = "";
									
									if(endDate != null)
									{
										if(endDate.getHours() >= 12)
										{
											pmSelected = "selected='yes'";
										}
										else
										{
											amSelected = "selected='yes'";
										}
									}

									out.println("<option value='am' " + amSelected + ">AM</option>");
									out.println("<option value='pm' " + pmSelected + ">PM</option>");
							%>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<br/>
							<hr width="100%" size="3" color="gray"/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							<font color="red">*</font> Event Description: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<textarea name="event_description" cols="40" rows="5" wrap="soft">Enter Event Description Here</textarea>
							<% } else {
								out.println("<textarea name='event_description' cols='40' rows='5' wrap='soft'>" + event.get(Models.Event.EventInfo.GEN_INFO) + "</textarea>");
							   } %>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Venue Description: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<textarea name="venue_description" cols="40" rows="5" wrap="soft">Enter Venue Description Here</textarea>
							<% } else {
								out.println("<textarea name='venue_description' cols='40' rows='5' wrap='soft'>" + event.get(Models.Event.EventInfo.VENUE_INFO) + "</textarea>");
							   } %>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Cost & Payment Method: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<textarea name="cost_description" cols="40" rows="5" wrap="soft">Enter Cost & Payment Method Here</textarea>
							<% } else {
								out.println("<textarea name='cost_description' cols='40' rows='5' wrap='soft'>" + event.get(Models.Event.EventInfo.PRICE_INFO) + "</textarea>");
							   } %>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							<font color="red">*</font> Directions: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<textarea name="directions" cols="40" rows="5" wrap="soft">Enter Commute and/or Driving Directions Here</textarea>
							<% } else {
								out.println("<textarea name='directions' cols='40' rows='5' wrap='soft'>" + event.get(Models.Event.EventInfo.TRANSPORT_INFO) + "</textarea>");
							   } %>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Things to be Aware Of: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<textarea name="awareness_info" cols="40" rows="5" wrap="soft">Enter Things that Attendees Should be Aware Of Here</textarea>
							<% } else {
								out.println("<textarea name='awareness_info' cols='40' rows='5' wrap='soft'>" + event.get(Models.Event.EventInfo.AWARENESS_INFO) + "</textarea>");
							   } %>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Other Info: 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<textarea name="other_description" cols="40" rows="5" wrap="soft">Enter Other Info Here</textarea>
							<% } else {
								out.println("<textarea name='other_description' cols='40' rows='5' wrap='soft'>" + event.get(Models.Event.EventInfo.OTHER_INFO) + "</textarea>");
							   } %>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Video Link(s): 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<textarea name="video_links" cols="40" rows="5" wrap="soft">Enter Video Links Here</textarea>
							<% } else {
								out.println("<textarea name='video_links' cols='40' rows='5' wrap='soft'>" + event.get(Models.Event.EventInfo.VIDEOS) + "</textarea>");
							   } %>
							<br/>
							<i>(Separate with semicolon ;)</i>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Other Link(s): 
						</td>
						<td>
							<% if (request.getParameter("eventId") == null) {%>
								<textarea name="other_links" cols="40" rows="5" wrap="soft">Enter Other Description Here</textarea>
							<% } else {
								out.println("<textarea name='other_links' cols='40' rows='5' wrap='soft'>" + event.get(Models.Event.EventInfo.LINKS) + "</textarea>");
							   } %>
							<br/>
							<i>(Separate with semicolon ;)</i>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<% if (request.getParameter("eventId") == null) {%>
								<input class="button1" type="submit" value="Create Event"/>
								<a class="button1" href="citypage.jsp?city=<%= request.getParameter("locationId") %>">Cancel</button>
							<% } else {
								out.println("<input class='button1' type='submit' value='Update Event'/>");
								int eventId = Integer.parseInt(request.getParameter("eventId"));
								out.println("<a class='button1' href='EventPage?eventID=" + eventId + "'>Cancel</button>");
							   } %>
						</td>
					</tr>
				</table>	
			</form>
		</div>
	</div>
</div>
</body>
</html>
