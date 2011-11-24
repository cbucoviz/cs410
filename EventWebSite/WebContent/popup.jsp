<html> 
<head>    
    <title>Event Search</title>    
</head> 
<body>
    <%@ page import="java.util.*" %>
    
	<%

		Map<Models.Event.EventInfo, String> event = Models.Event.getExistingEvent(Integer.parseInt(request.getParameter("eventId")));
		
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
		out.println("<i>" + event.get(Models.Event.EventInfo.GEN_INFO) + "</i>");
	%>
</body> 
</html>
