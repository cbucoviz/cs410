<html> 
<head>    
    <title>Event Search</title>    
</head> 
<body>

	<button class="button1" name="attend_button" value="attend_event" style="float: right">Attend</button>
    <%@ page import="java.util.*" %>
    
	<%

		Map<Models.Event.EventInfo, String> event = Models.Event.getExistingEvent(Integer.parseInt(request.getParameter("eventId")));
		
		out.println("<b>Rating: </b><i>" + event.get(Models.Event.EventInfo.RATING) + "</i>");
		out.println("<br>");
		out.println("<b>Date: </b><i>" + event.get(Models.Event.EventInfo.EVENT_DATE) + "</i>");
		out.println("<br>");
		out.println("<b>Start Time: </b><i>" + event.get(Models.Event.EventInfo.START_TIME) + "</i>");
		out.println("<br>");
		
		if (event.get(Models.Event.EventInfo.VENUE) != null)
		{
			out.println("<b>Venue: </b><i>" + event.get(Models.Event.EventInfo.VENUE) + "</i>");
			out.println("<br>");
		}
		
		out.println("<b>Address: </b><i>" + event.get(Models.Event.EventInfo.ADDRESS) + "</i>");
		out.println("<br>");
		out.println("<br>");
		
		if (event.get(Models.Event.EventInfo.GEN_INFO).length() > 300)
		{
			out.println("<i>" + event.get(Models.Event.EventInfo.GEN_INFO).substring(0, 300) + "...</i>");
		}
		else
		{
			out.println("<i>" + event.get(Models.Event.EventInfo.GEN_INFO) + "</i>");
		}
		
	%>
</body> 
</html>
