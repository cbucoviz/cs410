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


<div id="edit_event_container">

	<!--  change the css style {display} of create_event_div and edit_event_div to toggle the two views -->
	
	<!-- Use this for CREATING NEW EVENT; set css style {display:none} to hide it and {display:block} to display it -->
	
	<div id="create_event_div">
	
		<div class="edit_event_header">
			<h2><b>Create Event</b></h2>
		</div>
		
		<div class="edit_event_content">
		
		<%
			// first things first, let's determine if we're creating an event at a particular locale
			Models.LocationAddress address = new Models.LocationAddress("","","");			
			Object locationObj = request.getParameter("locationId");
			if(locationObj != null)
			{
				Integer locationId = Integer.parseInt((String) locationObj);
				address = Models.Location.getLocationAddress(locationId);
			}
		%>

			<form action="EditEvent" method="POST">
				<table style="margin-left: 200px">
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
							<input type="text" name="title" size="25">
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> City: 
						</td>
						<td>
							<input type="text" name="city" size="25" value="<%= address.city %>">
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> State: 
						</td>
						<td>
							<input type="text" name="state" size="25" value="<%= address.state %>">
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> Country: 
						</td>
						<td>
							<input type="text" name="country" size="25" value="<%= address.country %>">
						</td>
					</tr>
					<tr>
						<td align="right">
							Venue: 
						</td>
						<td>
							<input type="text" name="venue" size="25">
							<i>(e.g. Rogers Arena)</i>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> Address: 
						</td>
						<td>
							<input type="text" name="address" size="25">
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
						
						<input type="text" name="date" size="25"> dd/mm/yyyy
							
						
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> Start Time: 
						</td>
						<td>
							<select name="start_hour">
								<option value="01">01</option>
								<option value="02">02</option>
								<option value="03">03</option>
								<option value="04">04</option>
								<option value="05">05</option>
								<option value="06">06</option>
								<option value="07">07</option>
								<option value="08">08</option>
								<option value="09">09</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
							</select>
							:
							<select name="start_minute">
								<option value="00">00</option>
								<option value="05">05</option>
								<option value="10">10</option>
								<option value="15">15</option>
								<option value="20">20</option>
								<option value="25">25</option>
								<option value="30">30</option>
								<option value="35">35</option>
								<option value="40">40</option>
								<option value="45">45</option>
								<option value="50">50</option>
								<option value="55">55</option>
							</select>
							<select name="start_am_pm">
								<option value="am">AM</option>
								<option value="pm">PM</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							<font color="red">*</font> End Time: 
						</td>
						<td>
							<select name="end_hour">
								<option value="01">01</option>
								<option value="02">02</option>
								<option value="03">03</option>
								<option value="04">04</option>
								<option value="05">05</option>
								<option value="06">06</option>
								<option value="07">07</option>
								<option value="08">08</option>
								<option value="09">09</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
							</select>
							:
							<select name="end_minute">
								<option value="00">00</option>
								<option value="05">05</option>
								<option value="10">10</option>
								<option value="15">15</option>
								<option value="20">20</option>
								<option value="25">25</option>
								<option value="30">30</option>
								<option value="35">35</option>
								<option value="40">40</option>
								<option value="45">45</option>
								<option value="50">50</option>
								<option value="55">55</option>
							</select>
							<select name="end_am_pm">
								<option value="am">AM</option>
								<option value="pm">PM</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
						<font color="red">*</font> Time Zone:
						</td>
						<td colspan="2">
							<select name="time_zone">
								<option value="PST">PST</option>
								<option value="EST">EST</option>
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
							<textarea name="event_description" cols="40" rows="5" wrap="soft">Enter Event Description Here</textarea>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Venue Description: 
						</td>
						<td>
							<textarea name="venue_description" cols="40" rows="5" wrap="soft">Enter Venue Description Here</textarea>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Cost & Payment Method: 
						</td>
						<td>
							<textarea name="cost_description" cols="40" rows="5" wrap="soft">Enter Cost & Payment Method Here</textarea>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							<font color="red">*</font> Directions: 
						</td>
						<td>
							<textarea name="directions" cols="40" rows="5" wrap="soft">Enter Commute and/or Driving Directions Here</textarea>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Other Info: 
						</td>
						<td>
							<textarea name="other_description" cols="40" rows="5" wrap="soft">Enter Other Info Here</textarea>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							Video Link(s): 
						</td>
						<td>
							<textarea name="video_links" cols="40" rows="5" wrap="soft">Enter Video Links Here</textarea>
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
							<textarea name="other_links" cols="40" rows="5" wrap="soft">Enter Other Description Here</textarea>
							<br/>
							<i>(Separate with semicolon ;)</i>
							<br/>
							<br/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input class="button1" type="submit" value="Create Event"/>
							<button class="button1" type="button" value="Cancel" onclick="location.href='citypage.jsp'">Cancel</button>
						</td>
					</tr>
				</table>	
			</form>
		</div>
	</div>	
</div>
</body>
</html>
