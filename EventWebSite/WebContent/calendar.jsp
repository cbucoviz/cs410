<%@page import="Models.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="config/default.css" rel="stylesheet" type="text/css" />
<link href="config/fullcalendar.css" rel="stylesheet" type="text/css"/>
<link href='config/fullcalendar.print.css'  rel='stylesheet' type='text/css' media='print' />
<script type='text/javascript' src='plugins/fullcalendar.js'></script>
<script type='text/javascript' src='scrips/forwarder.js'></script>
<script type='text/javascript'>

	$(document).ready(function() {
	
		
		$('#calendar').fullCalendar({
			editable: false,
			events: [


		<%
			Boolean loggedIn = (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN);
			
			if(loggedIn != null && loggedIn == true)
			{
				// we're logged in, get all the events for this user
				java.util.HashSet<Models.CalendarEvent> calendarEvents = Models.User.getAttendingEvents((Integer) session.getAttribute(Servlets.SessionVariables.USER_ID));
				java.util.Iterator<Models.CalendarEvent> myItr = calendarEvents.iterator();
						
				while(myItr.hasNext())
				{
					
					out.println(myItr.next().getJQueryCalendarString());
				}
			}
			
		%>
			],
			eventClick: function(event) {
		        if (event.url) {
		            $("#mainContent").load(event.url);
		            return false;
		        }
		    },
			timeFormat: 'h:mm tt'
		});
		
	});

</script>
<style type='text/css'>

	body {
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		}

	#calendar {
		width: 900px;
		margin: 0 auto;
		}

</style>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<center>
<table border=0 cellpadding=5 cellspacing=10>
<tr>
<td bgcolor="white">
	<div id='calendar'></div>
</td>
</tr>
</table>
</center>
</body>
</html>